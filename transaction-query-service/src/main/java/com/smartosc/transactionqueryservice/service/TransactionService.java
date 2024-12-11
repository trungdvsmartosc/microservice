package com.smartosc.transactionqueryservice.service;

import com.smartosc.transactionqueryservice.exception.ApiExceptionResponse;
import com.smartosc.transactionqueryservice.model.dto.TransactionDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService {
    ResponseEntity<ApiExceptionResponse<List<TransactionDto>>> getTransactionsByUserId(Long id);
}
