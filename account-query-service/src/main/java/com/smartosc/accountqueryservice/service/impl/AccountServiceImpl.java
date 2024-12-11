package com.smartosc.accountqueryservice.service.impl;

import com.smartosc.accountqueryservice.exception.ApiExceptionResponse;
import com.smartosc.accountqueryservice.model.dto.AccountDto;
import com.smartosc.accountqueryservice.model.entity.Account;
import com.smartosc.accountqueryservice.model.mapper.AccountMapper;
import com.smartosc.accountqueryservice.repository.AccountRepository;
import com.smartosc.accountqueryservice.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public ResponseEntity<ApiExceptionResponse<AccountDto>> findByAccountNumber(final String accountNumber) {
        final Optional<Account> account = accountRepository.findByNumber(accountNumber);
        return account.map(value -> ApiExceptionResponse.ok("Success", accountMapper.convertToAccountDto(value)))
                .orElseGet(() -> ApiExceptionResponse.notFound("Account with number " + accountNumber + " does not exist"));
    }

    @Override
    public ResponseEntity<ApiExceptionResponse<AccountDto>> findById(final Long id) {
        final Optional<Account> account = accountRepository.findById(id);
        return account.map(value -> ApiExceptionResponse.ok("Success", accountMapper.convertToAccountDto(value)))
                .orElseGet(() -> ApiExceptionResponse.notFound("Account with id " + id + " does not exist"));
    }
}
