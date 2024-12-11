package com.smartosc.transactioncommandservice.controller;

import com.smartosc.transactioncommandservice.exception.ApiExceptionResponse;
import com.smartosc.transactioncommandservice.model.dto.TransactionDto;
import com.smartosc.transactioncommandservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ApiExceptionResponse<TransactionDto>> createTransaction(@RequestBody TransactionDto transactionDto) {
        return transactionService.createTransaction(transactionDto);
    }
}
