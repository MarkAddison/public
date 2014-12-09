package com.addison.database.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false, insertable = false, updatable = false)
    private Long accountId;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", nullable = false)
    private java.util.Date creationDate = new Date();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Trade> trades = new HashSet<Trade>();

    public Account(String name) {
        this.name = name;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public java.util.Date getCreationDate() {
        return creationDate;
    }

    public Set<Trade> getTrades() {
        return trades;
    }

    public void addTrade(Trade trade) {
        trades.add(trade);
    }
}
