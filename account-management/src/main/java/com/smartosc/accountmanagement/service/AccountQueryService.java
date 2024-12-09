package com.smartosc.accountmanagement.service;

import com.smartosc.accountmanagement.model.dto.AccountDto;
import com.smartosc.accountmanagement.model.entity.Account;

public interface AccountQueryService {

    Account create(AccountDto account);

    Account update(String accountNumber, AccountDto account);

    Account findByAccountNumber(String accountNumber);

    Account findById(Long id);
}
