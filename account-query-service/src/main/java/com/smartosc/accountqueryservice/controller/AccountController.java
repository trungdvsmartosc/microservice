package com.smartosc.accountqueryservice.controller;

import com.smartosc.accountqueryservice.model.dto.AccountDto;
import com.smartosc.accountqueryservice.model.entity.Account;
import com.smartosc.accountqueryservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/account-number")
    public ResponseEntity<AccountDto> findByAccountNumber(@RequestParam("account_number") String accountNumber) {
        return ResponseEntity.ok(accountService.findByAccountNumber(accountNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(accountService.findById(id));
    }

    @GetMapping("/account-number-internal")
    public ResponseEntity<Account> findByAccountNumberInternal(@RequestParam("account_number") String accountNumber) {
        return ResponseEntity.ok(accountService.findByAccountNumberInternal(accountNumber));
    }
}
