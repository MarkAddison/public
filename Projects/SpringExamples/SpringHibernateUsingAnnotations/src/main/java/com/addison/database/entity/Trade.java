package com.addison.database.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id", nullable = false, insertable = false, updatable = false)
    private Long tradeId;

    @Column(name = "buySell", nullable = false, length = 4)
    private String buySell;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "symbol", nullable = true, length = 20)
    private String symbol;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne(cascade = CascadeType.ALL)
    private Account account;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "trade_date", nullable = false)
    public java.util.Date tradeDate = new java.util.Date();

    public Trade(String buySell, Double quantity, String symbol, Double price, Account account) {
        this.buySell = buySell;
        this.quantity = quantity;
        this.symbol = symbol;
        this.price = price;
        this.account = account;
        account.addTrade(this);
    }

    public Long getTradeId() {
        return tradeId;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getBuySell() {
        return buySell;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public java.util.Date getTradeDate() {
        return tradeDate;
    }

    public Account getAccount() {
        return account;
    }
}