package com.jts.banking.service;

import com.jts.banking.domain.exception.AccountNotFoundException;
import com.jts.banking.domain.exception.InsufficientFundsException;
import com.jts.banking.domain.model.Account;
import com.jts.banking.domain.model.Customer;
import com.jts.banking.domain.model.Transaction;
import com.jts.banking.domain.model.TransactionType;
import com.jts.banking.repository.AccountRepository;
import com.jts.banking.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountCommandService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountCommandService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account createAccount(String customerName, BigDecimal initialDeposit) {
        Customer customer = new Customer(UUID.randomUUID().toString(), customerName);
        Account account = new Account(UUID.randomUUID().toString(), customer, initialDeposit);

        if (initialDeposit.compareTo(BigDecimal.ZERO) > 0) {
            Transaction transaction = new Transaction(TransactionType.DEPOSIT, initialDeposit, account.getId());
            account.addTransaction(transaction);
            transactionRepository.save(transaction);
        }

        accountRepository.save(account);
        return account;

    }

    public void deposit(String accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found : " + accountId));

        account.deposit(amount);

        Transaction transaction = new Transaction(TransactionType.DEPOSIT, amount, account.getId());
        account.addTransaction(transaction);
        transactionRepository.save(transaction);
    }

    public void withdraw(String accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found : " + accountId));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Not enough funds for withdraw");
        }
        account.withdraw(amount);

        Transaction transaction = new Transaction(TransactionType.WITHDRAWAL, amount, account.getId());
        account.addTransaction(transaction);
        transactionRepository.save(transaction);
    }

    public void transfer(String fromAccountId, String toAccountId, BigDecimal amount) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new AccountNotFoundException("The source account not found : " + fromAccountId));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new AccountNotFoundException("The destination account not found : " + toAccountId));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Not enough funds for transfer");
        }

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        Transaction transferTransaction = new Transaction(TransactionType.TRANSFER, amount, fromAccountId, toAccountId);
        transactionRepository.save(transferTransaction);
        fromAccount.addTransaction(transferTransaction);
        toAccount.addTransaction(transferTransaction);
    }
}
