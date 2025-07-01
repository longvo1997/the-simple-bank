package com.jts.banking.controller;

import com.jts.banking.controller.model.CreateAccountRequest;
import com.jts.banking.controller.model.TransactionRequest;
import com.jts.banking.controller.model.TransferRequest;
import com.jts.banking.domain.model.Account;
import com.jts.banking.domain.model.Transaction;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Accounts")
@RequestMapping("/api/accounts")
public interface AccountApi {

    @PostMapping
    ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest request);

    @GetMapping("/{accountId}/balance")
    ResponseEntity<BigDecimal> getBalance(@PathVariable String accountId);

    @GetMapping("/{accountId}/transactions")
    ResponseEntity<List<Transaction>> getTransactions(@PathVariable String accountId);

    @PostMapping("/{accountId}/deposit")
    ResponseEntity<Void> deposit(@PathVariable String accountId, @RequestBody TransactionRequest request);

    @PostMapping("/{accountId}/withdraw")
    ResponseEntity<Void> withdraw(@PathVariable String accountId, @RequestBody TransactionRequest request);

    @PostMapping("/{fromAccountId}/transfer")
    ResponseEntity<Void> transfer(@PathVariable String fromAccountId, @RequestBody TransferRequest request);
} 