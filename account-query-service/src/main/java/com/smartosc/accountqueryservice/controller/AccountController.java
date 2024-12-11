package com.smartosc.accountqueryservice.controller;

import com.smartosc.accountqueryservice.exception.ApiExceptionResponse;
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
    public ResponseEntity<ApiExceptionResponse<AccountDto>> findByAccountNumber(@RequestParam("account_number") String accountNumber) {
        return accountService.findByAccountNumber(accountNumber);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiExceptionResponse<AccountDto>> findById(@PathVariable("id") Long id) {
        return accountService.findById(id);
    }
}
