package com.jts.banking.controller;

import com.jts.banking.domain.model.Account;
import com.jts.banking.domain.model.Transaction;
import com.jts.banking.service.BankManagerQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class BankManagerController implements BankManagerApi {

    private final BankManagerQueryService bankManagerQueryService;

    public BankManagerController(BankManagerQueryService bankManagerQueryService) {
        this.bankManagerQueryService = bankManagerQueryService;
    }

    @Override
    public ResponseEntity<BigDecimal> getTotalBalance() {
        return ResponseEntity.ok(bankManagerQueryService.getTotalBankBalance());
    }

    @Override
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(bankManagerQueryService.getAllTransactions());
    }

    @Override
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(bankManagerQueryService.getAllAccounts());
    }
} 