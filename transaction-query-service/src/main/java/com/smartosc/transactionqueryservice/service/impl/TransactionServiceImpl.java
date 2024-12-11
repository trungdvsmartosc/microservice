package com.smartosc.transactionqueryservice.service.impl;

import com.smartosc.transactionqueryservice.exception.ApiExceptionResponse;
import com.smartosc.transactionqueryservice.external.AccountService;
import com.smartosc.transactionqueryservice.model.dto.TransactionDto;
import com.smartosc.transactionqueryservice.model.entity.Transaction;
import com.smartosc.transactionqueryservice.model.mapper.TransactionMapper;
import com.smartosc.transactionqueryservice.repository.TransactionRepository;
import com.smartosc.transactionqueryservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Override
    public ResponseEntity<ApiExceptionResponse<List<TransactionDto>>> getTransactionsByUserId(final Long id) {
        final List<Transaction> transactions = transactionRepository.findTransactionsBySenderAccountOrReceiverAccountId(id);
        return ApiExceptionResponse.ok("Success", transactions.stream().map(TransactionMapper.INSTANCE::convertToTransactionDto).toList());
    }
}
