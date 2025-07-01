package com.jts.banking.service;

import com.jts.banking.domain.model.Account;
import com.jts.banking.domain.model.Transaction;
import com.jts.banking.repository.AccountRepository;
import com.jts.banking.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class BankManagerQueryService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;



    public BankManagerQueryService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public BigDecimal getTotalBankBalance() {
        return accountRepository.findAll().stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
