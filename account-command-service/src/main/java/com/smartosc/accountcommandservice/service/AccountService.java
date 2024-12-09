package com.smartosc.accountcommandservice.service;

import com.smartosc.accountcommandservice.model.dto.AccountDto;
import com.smartosc.accountcommandservice.model.entity.Account;

public interface AccountService {

    Account create(AccountDto account);

    Account update(String accountNumber, AccountDto account);
}
