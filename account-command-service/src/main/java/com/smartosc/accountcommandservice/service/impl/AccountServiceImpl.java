package com.smartosc.accountcommandservice.service.impl;

import com.smartosc.accountcommandservice.exception.ApiException;
import com.smartosc.accountcommandservice.exception.ApiExceptionResponse;
import com.smartosc.accountcommandservice.model.dto.AccountDto;
import com.smartosc.accountcommandservice.model.entity.Account;
import com.smartosc.accountcommandservice.model.mapper.AccountMapper;
import com.smartosc.accountcommandservice.repository.AccountRepository;
import com.smartosc.accountcommandservice.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public ResponseEntity<ApiExceptionResponse<AccountDto>> create(final AccountDto accountDto) {
        final String accountNumber = accountDto.getNumber();
        if (accountRepository.existsByNumber(accountNumber)) {
            return ApiExceptionResponse.conflict("Account with number " + accountNumber + " already exists");
        }

        final var currentTime = Instant.now();
        final Account account = accountMapper.convertToAccount(accountDto);
        account.setBalance(0);
        account.setCreatedAt(currentTime);
        account.setModifiedAt(currentTime);
        account.setStatus(false);

        final Account savedAccount = accountRepository.save(account);
        return ApiExceptionResponse.created("Account created successfully", accountMapper.convertToAccountDto(savedAccount));
    }

    @Override
    public ResponseEntity<ApiExceptionResponse<AccountDto>> updateBalance(final String accountNumber, final AccountDto accountDto) {
        final Optional<Account> optionalAccount = accountRepository.findByNumber(accountNumber);
        if (optionalAccount.isEmpty()) {
            return ApiExceptionResponse.notFound("Account with number " + accountNumber + " does not exist");
        }
        final Account oldAccount = optionalAccount.get();
        oldAccount.setBalance(accountDto.getBalance());
        final Account updatedAccount = accountRepository.save(oldAccount);
        return ApiExceptionResponse.ok("Account updated successfully", accountMapper.convertToAccountDto(updatedAccount));
    }
}
