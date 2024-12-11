package com.smartosc.transactioncommandservice.external;

import com.smartosc.transactioncommandservice.exception.ApiExceptionResponse;
import com.smartosc.transactioncommandservice.model.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ACCOUNT-QUERY-SERVICE", path = "/accounts")
public interface AccountQueryService {

    @GetMapping("/{id}")
    ResponseEntity<ApiExceptionResponse<AccountDto>> findById(@PathVariable("id") Long id);

    @GetMapping("/account-number")
    ResponseEntity<ApiExceptionResponse<AccountDto>> findByAccountNumber(@RequestParam("account_number") String accountNumber);
}
