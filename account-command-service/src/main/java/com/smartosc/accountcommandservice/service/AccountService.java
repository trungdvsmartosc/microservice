package com.smartosc.accountcommandservice.service;

import com.smartosc.accountcommandservice.exception.ApiExceptionResponse;
import com.smartosc.accountcommandservice.model.dto.AccountDto;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    ResponseEntity<ApiExceptionResponse<AccountDto>> create(AccountDto account);

    ResponseEntity<ApiExceptionResponse<AccountDto>> updateBalance(String accountNumber, AccountDto account);
}
