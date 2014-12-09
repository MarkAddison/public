package com.addison.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.addison.database.entity.Account;
import com.addison.database.entity.Trade;

public class RunTradeAccounts implements Runnable {
    private static Logger logger = Logger.getLogger(RunTradeAccounts.class.getName());
    private static final String LON_VOD = "LON:VOD";

    public static void main(String[] args) {
        logger.info("Maven + Hibernate + SQL  One to Many Using Annotations ");
        RunTradeAccounts app = new RunTradeAccounts();
        app.run();
    }

    @Override
    public void run() {
        Session currentSession = HibernateUtil.getSessionFactory().openSession();

        persistObjects(currentSession);
        showPositions(currentSession);

        currentSession.close();
    }

    private void persistObjects(Session currentSession) {
        Account barcAccount = new Account("BARC");
        Transaction tx = currentSession.beginTransaction();
        currentSession.persist(barcAccount);
        currentSession.flush();

        Account gsAccount = new Account("GS");
        currentSession.persist(gsAccount);
        currentSession.flush();

        Trade trade = new Trade("Buy", 100.0, "NASDAQ:MSFT", 48.42D, barcAccount);
        currentSession.persist(trade);
        currentSession.flush();

        trade = new Trade("Buy", 200.0, LON_VOD, 230.20D, gsAccount);
        currentSession.persist(trade);
        currentSession.flush();

        trade = new Trade("Sell", 100.0, LON_VOD, 230.555D, gsAccount);
        currentSession.persist(trade);
        currentSession.flush();
        tx.commit();
    }

    private void showPositions(Session currentSession) {
        String accountNames[] = { "GS", "BARC" };
        for (String accountName : accountNames) {
            showPositions(currentSession, accountName);
        }
    }

    private void showPositions(Session currentSession, String name) {
        @SuppressWarnings("unchecked")
        Transaction tx = currentSession.beginTransaction();
        List<Account> accounts = currentSession.createQuery("from Account a where a.name = '" + name + "'").list();
        Set<Trade> trades = accounts.get(0).getTrades();

        Map<String, Double> positions = new HashMap<String, Double>();
        for (Trade trade : trades) {
            String symbol = trade.getSymbol();
            Double currentPosition = positions.get(symbol);
            if (currentPosition == null) {
                currentPosition = new Double(0.0D);
            }

            if ("Buy".equals(trade.getBuySell())) {
                currentPosition = currentPosition + trade.getQuantity();
            } else if ("Sell".equals(trade.getBuySell())) {
                currentPosition = currentPosition - trade.getQuantity();
            }
            positions.put(symbol, currentPosition);
        }
        tx.commit();

        logger.info("Positions for " + name + ":");
        for (String symbol : positions.keySet()) {
            Double currentPosition = positions.get(symbol);
            logger.info("  " + symbol + "\t" + currentPosition);
        }
    }
}
