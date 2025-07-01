package com.jts.banking.service;

import com.jts.banking.domain.exception.AccountNotFoundException;
import com.jts.banking.domain.model.Account;
import com.jts.banking.domain.model.Transaction;
import com.jts.banking.repository.AccountRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountQueryService {

    private final AccountRepository accountRepository;


    public AccountQueryService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public BigDecimal getBalance(String accountId) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException("Account not found : " + accountId));
        return account.getBalance();
    }

    public List<Transaction> getTransactions(String accountId) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException("Account not found : " + accountId));
        return account.getTransactions();
    }
}
