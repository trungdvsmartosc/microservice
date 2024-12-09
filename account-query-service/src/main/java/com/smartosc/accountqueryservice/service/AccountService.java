package com.smartosc.accountqueryservice.service;

import com.smartosc.accountqueryservice.model.dto.AccountDto;
import com.smartosc.accountqueryservice.model.entity.Account;

public interface AccountService {

    AccountDto findByAccountNumber(String accountNumber);

    AccountDto findById(Long id);

    Account findByAccountNumberInternal(String accountNumber);
}
