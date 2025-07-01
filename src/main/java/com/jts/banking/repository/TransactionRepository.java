package com.jts.banking.repository;

import com.jts.banking.domain.model.Transaction;
import java.util.List;


public interface TransactionRepository {

    Transaction save(Transaction transaction);
    List<Transaction> findAll();
    void deleteAll();

}
