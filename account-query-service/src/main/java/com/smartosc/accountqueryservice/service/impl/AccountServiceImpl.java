package com.smartosc.accountqueryservice.service.impl;

import com.smartosc.accountqueryservice.exception.ApiException;
import com.smartosc.accountqueryservice.model.dto.AccountDto;
import com.smartosc.accountqueryservice.model.entity.Account;
import com.smartosc.accountqueryservice.model.mapper.AccountMapper;
import com.smartosc.accountqueryservice.repository.AccountRepository;
import com.smartosc.accountqueryservice.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountDto findByAccountNumber(final String accountNumber) {
        final Account account = findByAccountNumberInternal(accountNumber);
        return accountMapper.convertToAccountDto(account);
    }

    @Override
    public AccountDto findById(final Long id) {
        final Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ApiException("Account with id " + id + " does not exist", HttpStatus.NOT_FOUND));
        return accountMapper.convertToAccountDto(account);
    }

    @Override
    public Account findByAccountNumberInternal(String accountNumber) {
        return accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new ApiException("Account with number " + accountNumber + " does not exist", HttpStatus.NOT_FOUND));
    }
}
