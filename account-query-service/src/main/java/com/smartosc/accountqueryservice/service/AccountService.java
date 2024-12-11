package com.smartosc.accountqueryservice.service;

import com.smartosc.accountqueryservice.exception.ApiExceptionResponse;
import com.smartosc.accountqueryservice.model.dto.AccountDto;
import com.smartosc.accountqueryservice.model.entity.Account;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    ResponseEntity<ApiExceptionResponse<AccountDto>> findByAccountNumber(String accountNumber);

    ResponseEntity<ApiExceptionResponse<AccountDto>> findById(Long id);
}
