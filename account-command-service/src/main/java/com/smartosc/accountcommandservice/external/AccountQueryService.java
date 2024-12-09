package com.smartosc.accountcommandservice.external;

import com.smartosc.accountcommandservice.model.dto.AccountDto;
import com.smartosc.accountcommandservice.model.entity.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ACCOUNT-QUERY-SERVICE", path = "/accounts")
public interface AccountQueryService {

    @GetMapping("/account-number-internal")
    ResponseEntity<Account> findByAccountNumberInternal(@RequestParam("account_number") String accountNumber);

    @GetMapping("/{id}")
    ResponseEntity<AccountDto> findById(@PathVariable("id") Long id);
}
