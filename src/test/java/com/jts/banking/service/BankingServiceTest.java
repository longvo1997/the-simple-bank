package com.jts.banking.service;

import com.jts.banking.domain.exception.AccountNotFoundException;
import com.jts.banking.domain.exception.InsufficientFundsException;
import com.jts.banking.domain.model.Account;
import com.jts.banking.domain.model.Transaction;
import com.jts.banking.domain.model.TransactionType;
import com.jts.banking.repository.AccountRepository;
import com.jts.banking.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankingServiceTest {

    @Autowired
    private AccountCommandService accountCommandService;

    @Autowired
    private AccountQueryService accountQueryService;

    @Autowired
    private BankManagerQueryService bankManagerQueryService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        accountRepository.deleteAll();
        transactionRepository.deleteAll();
    }

    @Test
    void testCustomerJoinAndCheckBalance() {
        Account account = accountCommandService.createAccount("Long Vo", new BigDecimal("100.00"));
        assertNotNull(account);
        assertEquals("Long Vo", account.getCustomer().getName());

        BigDecimal balance = accountQueryService.getBalance(account.getId());
        assertEquals(0, new BigDecimal("100.00").compareTo(balance));
    }

    @Test
    void testDeposit() {
        Account account = accountCommandService.createAccount("Minh Tran", new BigDecimal("50.00"));
        accountCommandService.deposit(account.getId(), new BigDecimal("25.50"));

        BigDecimal balance = accountQueryService.getBalance(account.getId());
        assertEquals(0, new BigDecimal("75.50").compareTo(balance));
    }

    @Test
    void testWithdrawSuccess() {
        Account account = accountCommandService.createAccount("Minh Tran", new BigDecimal("100.00"));
        accountCommandService.withdraw(account.getId(), new BigDecimal("30.00"));

        BigDecimal balance = accountQueryService.getBalance(account.getId());
        assertEquals(0, new BigDecimal("70.00").compareTo(balance));
    }

    @Test
    void testWithdrawFailInsufficientFunds() {
        Account account = accountCommandService.createAccount("anh virus", new BigDecimal("20.00"));
        assertThrows(InsufficientFundsException.class, () -> {
            accountCommandService.withdraw(account.getId(), new BigDecimal("30.00"));
        });
    }

    @Test
    void testViewOwnTransactions() {
        Account account = accountCommandService.createAccount("sena", new BigDecimal("200.00"));
        accountCommandService.deposit(account.getId(), new BigDecimal("50.00"));
        accountCommandService.withdraw(account.getId(), new BigDecimal("25.00"));

        List<Transaction> transactions = accountQueryService.getTransactions(account.getId());
        assertEquals(3, transactions.size());
        assertEquals(TransactionType.DEPOSIT, transactions.get(0).getType());
        assertEquals(TransactionType.DEPOSIT, transactions.get(1).getType());
        assertEquals(TransactionType.WITHDRAWAL, transactions.get(2).getType());
    }

    @Test
    void testTransferSuccess() {
        Account fromAccount = accountCommandService.createAccount("Sender", new BigDecimal("500.00"));
        Account toAccount = accountCommandService.createAccount("Receiver", new BigDecimal("100.00"));

        accountCommandService.transfer(fromAccount.getId(), toAccount.getId(), new BigDecimal("150.00"));

        BigDecimal fromBalance = accountQueryService.getBalance(fromAccount.getId());
        BigDecimal toBalance = accountQueryService.getBalance(toAccount.getId());

        assertEquals(0, new BigDecimal("350.00").compareTo(fromBalance));
        assertEquals(0, new BigDecimal("250.00").compareTo(toBalance));
    }

    @Test
    void testTransferFailInsufficientFunds() {
        Account fromAccount = accountCommandService.createAccount("Sender", new BigDecimal("100.00"));
        Account toAccount = accountCommandService.createAccount("Receiver", new BigDecimal("100.00"));

        assertThrows(InsufficientFundsException.class, () -> {
            accountCommandService.transfer(fromAccount.getId(), toAccount.getId(), new BigDecimal("150.00"));
        });
    }

    @Test
    void testBankManagerViewTotalBalance() {
        accountCommandService.createAccount("sena", new BigDecimal("1000.00"));
        accountCommandService.createAccount("kayff", new BigDecimal("2500.50"));

        BigDecimal totalBalance = bankManagerQueryService.getTotalBankBalance();
        assertEquals(0, new BigDecimal("3500.50").compareTo(totalBalance));
    }

    @Test
    void testBankManagerViewAllTransactions() {
        Account account1 = accountCommandService.createAccount("lam hong", new BigDecimal("1000.00"));
        Account account2 = accountCommandService.createAccount("nam van", new BigDecimal("500.00"));

        accountCommandService.deposit(account1.getId(), new BigDecimal("100.00"));
        accountCommandService.withdraw(account2.getId(), new BigDecimal("50.00"));
        accountCommandService.transfer(account1.getId(), account2.getId(), new BigDecimal("200.00"));

        List<Transaction> allTransactions = bankManagerQueryService.getAllTransactions();
        assertEquals(5, allTransactions.size());
    }
}
