package com.smartosc.accountmanagement.model.mapper;

import com.smartosc.accountmanagement.model.dto.AccountDto;
import com.smartosc.accountmanagement.model.entity.Account;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {Account.class, AccountDto.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AccountMapper {

    Account convertToAccount(AccountDto accountDto);

    AccountDto convertToAccountDto(Account account);
}
