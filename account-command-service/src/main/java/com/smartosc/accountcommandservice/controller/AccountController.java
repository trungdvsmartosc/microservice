package com.smartosc.accountcommandservice.controller;

import com.smartosc.accountcommandservice.exception.ApiExceptionResponse;
import com.smartosc.accountcommandservice.model.dto.AccountDto;
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
    public ResponseEntity<ApiExceptionResponse<AccountDto>> createAccount(@RequestBody AccountDto accountDto) {
        return accountService.create(accountDto);
    }

    @PutMapping
    public ResponseEntity<ApiExceptionResponse<AccountDto>> updateBalance(@RequestParam("account_number") String accountNumber, @RequestBody AccountDto account) {
        return accountService.updateBalance(accountNumber, account);
    }
}
