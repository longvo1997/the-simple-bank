package com.jts.banking.repository;

import com.jts.banking.domain.model.Transaction;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class InMemoryTransactionRepository implements TransactionRepository {

    private final List<Transaction> transactions = new CopyOnWriteArrayList<>();


    @Override
    public Transaction save(Transaction transaction) {
        transactions.add(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(transactions));
    }

    @Override
    public void deleteAll() {
        transactions.clear();
    }
}
