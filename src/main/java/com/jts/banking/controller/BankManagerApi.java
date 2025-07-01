package com.jts.banking.controller;

import com.jts.banking.domain.model.Account;
import com.jts.banking.domain.model.Transaction;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Bank Manager")
@RequestMapping("/api/manager")
public interface BankManagerApi {

    @GetMapping("/total-balance")
    ResponseEntity<BigDecimal> getTotalBalance();

    @GetMapping("/transactions")
    ResponseEntity<List<Transaction>> getAllTransactions();

    @GetMapping("/accounts")
    ResponseEntity<List<Account>> getAllAccounts();
} 