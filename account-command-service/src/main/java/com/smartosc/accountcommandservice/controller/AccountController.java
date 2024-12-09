package com.smartosc.accountcommandservice.controller;

import com.smartosc.accountcommandservice.model.dto.AccountDto;
import com.smartosc.accountcommandservice.model.entity.Account;
import com.smartosc.accountcommandservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Account> createAccount(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(accountService.create(accountDto));
    }

    @PutMapping
    public ResponseEntity<Account> updateAccount(@RequestParam("account_number") String accountNumber, @RequestBody AccountDto account) {
        return ResponseEntity.ok(accountService.update(accountNumber, account));
    }
}
