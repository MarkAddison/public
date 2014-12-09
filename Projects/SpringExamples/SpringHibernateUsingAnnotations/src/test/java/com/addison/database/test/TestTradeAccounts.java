package com.addison.database.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.addison.database.entity.Account;
import com.addison.database.entity.Trade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/testContext.xml")
@Transactional
public class TestTradeAccounts {
    private static final String LON_VOD = "LON:VOD";
    private Account barcAccount = new Account("BARC");
    private Account gsAccount = new Account("GS");

    @Autowired
    private SessionFactory sessionFactory;
    private Session currentSession;

    @Before
    public void openSession() {
        currentSession = sessionFactory.getCurrentSession();
    }

    @Test
    public void hasSessionFactory() {
        assertNotNull(sessionFactory);
    }

    @Test
    public void noObjectsPersistedAtStart() {
        List<?> results = currentSession.createQuery("from Account").list();
        assertTrue(results.isEmpty());
        results = currentSession.createQuery("from Trade").list();
        assertTrue(results.isEmpty());
    }

    @Test
    public void persistObjects() {
        assertEquals(0, currentSession.createQuery("from Account").list().size());
        currentSession.persist(barcAccount);
        currentSession.flush();
        assertEquals(1, currentSession.createQuery("from Account").list().size());

        currentSession.persist(gsAccount);
        currentSession.flush();
        assertEquals(2, currentSession.createQuery("from Account").list().size());

        Trade trade = new Trade("Buy", 100.0, "NASDAQ:MSFT", 48.42D, barcAccount);
        currentSession.persist(trade);
        currentSession.flush();
        assertEquals(1, currentSession.createQuery("from Trade").list().size());

        trade = new Trade("Buy", 200.0, LON_VOD, 230.20D, gsAccount);
        currentSession.persist(trade);
        currentSession.flush();
        assertEquals(2, currentSession.createQuery("from Trade").list().size());

        trade = new Trade("Sell", 100.0, LON_VOD, 230.555D, gsAccount);
        currentSession.persist(trade);
        currentSession.flush();
        assertEquals(3, currentSession.createQuery("from Trade").list().size());
    }

    @Test
    public void persistsThenQueryObjects() {
        persistObjects();
        assertEquals(1, currentSession.createQuery("from Account where name = 'BARC'").list().size());
        assertEquals(0, currentSession.createQuery("from Account where name = 'BITE'").list().size());
        assertEquals(1, currentSession.createQuery("from Account where name = 'GS'").list().size());

        assertEquals(1, currentSession.createQuery("from Trade where symbol = 'NASDAQ:MSFT'").list().size());
        assertEquals(2, currentSession.createQuery("from Trade where symbol = 'LON:VOD'").list().size());

        @SuppressWarnings("unchecked")
        List<Account> accountBarc = currentSession.createQuery("from Account a where a.name = 'BARC'").list();
        Set<Trade> trades = accountBarc.get(0).getTrades();
        assertEquals(1, trades.size());

        @SuppressWarnings("unchecked")
        List<Account> accountGs = currentSession.createQuery("from Account where name = 'GS'").list();
        trades = accountGs.get(0).getTrades();
        assertEquals(2, trades.size());
        for (Trade trade : trades) { // both trade are for LON_VOD
            assertEquals(LON_VOD, trade.getSymbol());
        }
    }
}