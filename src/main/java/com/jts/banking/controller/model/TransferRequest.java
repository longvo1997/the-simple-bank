package com.jts.banking.controller.model;

import java.math.BigDecimal;

public class TransferRequest {
    private String toAccountId;
    private BigDecimal amount;

    public String getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(String toAccountId) {
        if (toAccountId == null || toAccountId.trim().isEmpty()) {
            throw new IllegalArgumentException("ToAccountId must be non-null and not empty");
        }
        this.toAccountId = toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive and non-null");
        }
        this.amount = amount;
    }
}
