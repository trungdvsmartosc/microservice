package com.smartosc.transaction.external;

import com.smartosc.transaction.model.dto.AccountDto;
import com.smartosc.transaction.model.entity.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ACCOUNT-MANAGEMENT", path = "/accounts")
public interface AccountService {

    @GetMapping("/{id}")
    ResponseEntity<Account> findById(@PathVariable("id") Long id);

    @GetMapping("/account-number")
    ResponseEntity<Account> findByAccountNumber(@RequestParam("account_number") String accountNumber);

    @PutMapping
    ResponseEntity<Account> updateAccount(@RequestParam("account_number") String accountNumber, @RequestBody AccountDto account);
}
