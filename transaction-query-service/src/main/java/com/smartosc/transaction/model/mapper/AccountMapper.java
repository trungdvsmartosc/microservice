package com.smartosc.transaction.model.mapper;

import com.smartosc.transaction.model.dto.AccountDto;
import com.smartosc.transaction.model.entity.Account;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {Account.class, AccountDto.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AccountMapper {

    Account convertToAccount(AccountDto accountDto);

    AccountDto convertToAccountDto(Account account);
}
