package com.jts.banking.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private final TransactionType type;
    private final BigDecimal amount;
    private final LocalDateTime timestamp;
    private final String accountId;
    private final String fromAccountId;
    private final String toAccountId;

    public Transaction(TransactionType type, BigDecimal amount, String accountId) {
        this.type = type;
        this.amount = amount;
        this.accountId = accountId;
        this.fromAccountId = null;
        this.toAccountId = null;
        this.timestamp = LocalDateTime.now();
    }

    public Transaction(TransactionType type, BigDecimal amount, String fromAccountId, String toAccountId) {
        this.type = type;
        this.amount = amount;
        this.accountId = null;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.timestamp = LocalDateTime.now();
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }
}
