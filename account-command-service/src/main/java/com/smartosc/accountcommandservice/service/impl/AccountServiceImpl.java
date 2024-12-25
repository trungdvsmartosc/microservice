package com.smartosc.accountcommandservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartosc.accountcommandservice.event.Producer;
import com.smartosc.accountcommandservice.exception.ApiException;
import com.smartosc.accountcommandservice.exception.ApiExceptionResponse;
import com.smartosc.accountcommandservice.model.dto.AccountDto;
import com.smartosc.accountcommandservice.model.entity.Account;
import com.smartosc.accountcommandservice.model.mapper.AccountMapper;
import com.smartosc.accountcommandservice.repository.AccountRepository;
import com.smartosc.accountcommandservice.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final Producer producer;
    private final ObjectMapper objectMapper;

    @Override
    public void create(final AccountDto accountDto) {
        try {
            String jsonAccountDto = objectMapper.writeValueAsString(accountDto);
            producer.sendEvent("events-topic", jsonAccountDto);
            log.info("Sent transaction event {}", jsonAccountDto);
        } catch (JsonProcessingException e) {
            log.info("Transaction event has not been sent. Error {}", e.getMessage());
            throw new RuntimeException(e);
        }
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
