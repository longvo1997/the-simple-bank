package com.jts.banking.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private final String id;
    private final Customer customer;
    private BigDecimal balance;
    private final List<Transaction> transactions;
    private final Lock lock = new ReentrantLock();

    public Account(String id, Customer customer, BigDecimal initialDeposit) {
        this.id = id;
        this.customer = customer;
        this.balance = initialDeposit;
        this.transactions = new ArrayList<>();
    }

    public void deposit(BigDecimal amount) {
        lock.lock();
        try {
            balance = balance.add(amount);
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(BigDecimal amount) {
        lock.lock();
        try {
            balance = balance.subtract(amount);
        } finally {
            lock.unlock();
        }
    }

    public void addTransaction(Transaction transaction) {
        lock.lock();
        try {
            this.transactions.add(transaction);
        } finally {
            lock.unlock();
        }
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public BigDecimal getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    public List<Transaction> getTransactions() {
        lock.lock();
        try {
            return Collections.unmodifiableList(new ArrayList<>(transactions));
        } finally {
            lock.unlock();
        }
    }
} 