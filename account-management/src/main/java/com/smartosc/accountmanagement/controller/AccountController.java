package com.smartosc.accountmanagement.controller;

import com.smartosc.accountmanagement.model.dto.AccountDto;
import com.smartosc.accountmanagement.model.entity.Account;
import com.smartosc.accountmanagement.service.AccountQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountQueryService accountQueryService;

    @GetMapping("/account-number")
    public ResponseEntity<Account> findByAccountNumber(@RequestParam("account_number") String accountNumber) {
        return ResponseEntity.ok(accountQueryService.findByAccountNumber(accountNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(accountQueryService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Account> createAccount(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(accountQueryService.create(accountDto));
    }

    @PutMapping
    public ResponseEntity<Account> updateAccount(@RequestParam("account_number") String accountNumber, @RequestBody AccountDto account) {
        return ResponseEntity.ok(accountQueryService.update(accountNumber, account));
    }
}
