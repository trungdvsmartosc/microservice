package com.smartosc.transactioncommandservice.external;

import com.smartosc.transactioncommandservice.exception.ApiExceptionResponse;
import com.smartosc.transactioncommandservice.model.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ACCOUNT-COMMAND-SERVICE", path = "/accounts")
public interface AccountCommandService {

    @PutMapping
    ResponseEntity<ApiExceptionResponse<AccountDto>> updateBalance(@RequestParam("account_number") String accountNumber, @RequestBody AccountDto account);
}
