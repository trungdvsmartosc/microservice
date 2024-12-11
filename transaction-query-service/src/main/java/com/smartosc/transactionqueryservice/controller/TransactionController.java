package com.smartosc.transactionqueryservice.controller;

import com.smartosc.transactionqueryservice.exception.ApiExceptionResponse;
import com.smartosc.transactionqueryservice.model.dto.TransactionDto;
import com.smartosc.transactionqueryservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiExceptionResponse<List<TransactionDto>>> getTransactionsByUserId(@PathVariable("id") Long id) {
        return transactionService.getTransactionsByUserId(id);
    }
}
