package com.smartosc.accountcommandservice.service.impl;

import com.smartosc.accountcommandservice.exception.ApiException;
import com.smartosc.accountcommandservice.external.AccountQueryService;
import com.smartosc.accountcommandservice.model.dto.AccountDto;
import com.smartosc.accountcommandservice.model.entity.Account;
import com.smartosc.accountcommandservice.model.mapper.AccountMapper;
import com.smartosc.accountcommandservice.repository.AccountRepository;
import com.smartosc.accountcommandservice.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountQueryService accountQueryService;
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
        final Account oldAccount = accountQueryService.findByAccountNumberInternal(accountNumber).getBody();
        if (oldAccount == null) {
            throw new ApiException("Account with number " + accountNumber + " does not exist", HttpStatus.NOT_FOUND);
        }
        BeanUtils.copyProperties(accountDto, oldAccount);
        return accountRepository.save(oldAccount);
    }
}
