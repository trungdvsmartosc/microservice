package com.smartosc.transactioncommandservice.service;

import com.smartosc.transactioncommandservice.exception.ApiExceptionResponse;
import com.smartosc.transactioncommandservice.model.dto.TransactionDto;
import org.springframework.http.ResponseEntity;

public interface TransactionService {

    ResponseEntity<ApiExceptionResponse<TransactionDto>> createTransaction(TransactionDto transactionDto);
}
