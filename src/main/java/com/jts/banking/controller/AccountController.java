package com.jts.banking.controller;

import com.jts.banking.controller.model.CreateAccountRequest;
import com.jts.banking.controller.model.TransactionRequest;
import com.jts.banking.controller.model.TransferRequest;
import com.jts.banking.domain.model.Account;
import com.jts.banking.domain.model.Transaction;
import com.jts.banking.service.AccountCommandService;
import com.jts.banking.service.AccountQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountController implements AccountApi {

    private final AccountCommandService accountCommandService;
    private final AccountQueryService accountQueryService;


    public AccountController(AccountCommandService accountCommandService, AccountQueryService accountQueryService) {
        this.accountCommandService = accountCommandService;
        this.accountQueryService = accountQueryService;
    }

    @Override
    public ResponseEntity<Account> createAccount(CreateAccountRequest request) {
        Account account = accountCommandService.createAccount(request.getCustomerName(), request.getInitialDeposit());
        return ResponseEntity.ok(account);
    }

    @Override
    public ResponseEntity<BigDecimal> getBalance(String accountId) {
        return ResponseEntity.ok(accountQueryService.getBalance(accountId));
    }

    @Override
    public ResponseEntity<List<Transaction>> getTransactions(String accountId) {
        return ResponseEntity.ok(accountQueryService.getTransactions(accountId));
    }

    @Override
    public ResponseEntity<Void> deposit(String accountId, TransactionRequest request) {
        if (request == null || request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().build();
        }
        if (request.getAmount().compareTo(new BigDecimal("1.00")) < 0) { // Example minimum deposit
            return ResponseEntity.badRequest().body(null);
        }
        accountCommandService.deposit(accountId, request.getAmount());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> withdraw(String accountId, TransactionRequest request) {
        if (request == null || request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().build();
        }
        if (request.getAmount().compareTo(new BigDecimal("1.00")) < 0) { // Example minimum withdrawal
            return ResponseEntity.badRequest().body(null);
        }
        accountCommandService.withdraw(accountId, request.getAmount());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> transfer(String fromAccountId, TransferRequest request) {
        if (request == null || request.getAmount() == null || request.getToAccountId() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().build();
        }
        if (request.getAmount().compareTo(new BigDecimal("1.00")) < 0) { // Example minimum transfer
            return ResponseEntity.badRequest().body(null);
        }
        if (request.getAmount().compareTo(new BigDecimal("10000.00")) > 0) { // Example transfer limit
            return ResponseEntity.badRequest().body(null);
        }
        accountCommandService.transfer(fromAccountId, request.getToAccountId(), request.getAmount());
        return ResponseEntity.ok().build();
    }
}
