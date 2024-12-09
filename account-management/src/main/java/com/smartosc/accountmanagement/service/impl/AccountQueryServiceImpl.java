package com.smartosc.accountmanagement.service.impl;

import com.smartosc.accountmanagement.exception.ApiException;
import com.smartosc.accountmanagement.model.dto.AccountDto;
import com.smartosc.accountmanagement.model.entity.Account;
import com.smartosc.accountmanagement.model.mapper.AccountMapper;
import com.smartosc.accountmanagement.repository.AccountRepository;
import com.smartosc.accountmanagement.service.AccountQueryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class AccountQueryServiceImpl implements AccountQueryService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public Account create(final AccountDto accountDto) {
        final var currentTime = Instant.now();
        final Account account = accountMapper.convertToAccount(accountDto);
        account.setBalance(0);
        account.setCreatedAt(currentTime);
        account.setModifiedAt(currentTime);
        account.setStatus(false);
        return accountRepository.save(account);
    }

    @Override
    public Account update(final String accountNumber, final AccountDto accountDto) {
        final Account oldAccount = findByAccountNumber(accountNumber);
        BeanUtils.copyProperties(accountDto, oldAccount);
        return accountRepository.save(oldAccount);
    }

    @Override
    public Account findByAccountNumber(final String accountNumber) {
        return accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new ApiException("Account with number " + accountNumber + " does not exist", HttpStatus.NOT_FOUND));
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ApiException("Account with id " + id + " does not exist", HttpStatus.NOT_FOUND));
    }

    @Override
}
