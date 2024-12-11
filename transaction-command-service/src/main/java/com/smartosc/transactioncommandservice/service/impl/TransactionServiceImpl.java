package com.smartosc.transactioncommandservice.service.impl;

import com.smartosc.transactioncommandservice.exception.ApiException;
import com.smartosc.transactioncommandservice.exception.ApiExceptionResponse;
import com.smartosc.transactioncommandservice.external.AccountCommandService;
import com.smartosc.transactioncommandservice.model.dto.AccountDto;
import com.smartosc.transactioncommandservice.model.dto.TransactionDto;
import com.smartosc.transactioncommandservice.model.entity.Account;
import com.smartosc.transactioncommandservice.model.entity.Transaction;
import com.smartosc.transactioncommandservice.model.mapper.TransactionMapper;
import com.smartosc.transactioncommandservice.repository.AccountRepository;
import com.smartosc.transactioncommandservice.repository.TransactionRepository;
import com.smartosc.transactioncommandservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountCommandService accountCommandService;
    private final AccountRepository accountRepository;

    @Override
    public ResponseEntity<ApiExceptionResponse<TransactionDto>> createTransaction(TransactionDto transactionDto) {
        if (StringUtils.isEmpty(transactionDto.getType()) || transactionDto.getAmount() <= 0) {
            throw new IllegalArgumentException("Invalid transaction");
        }

        if (transactionDto.getType().equals(Transaction.TYPE.TRANSFER)) {
            return ApiExceptionResponse.created("Successful transaction", createTransferTransaction(transactionDto));
        }

        if (transactionDto.getType().equals(Transaction.TYPE.WITHDRAWAL)) {
            return ApiExceptionResponse.created("Successful transaction", createWithdrawalTransaction(transactionDto));
        }

        if (transactionDto.getType().equals(Transaction.TYPE.DEPOSIT)) {
            return ApiExceptionResponse.created("Successful transaction", createDepositTransaction(transactionDto));
        }
        throw new IllegalArgumentException("Invalid transaction");
    }

    TransactionDto createTransferTransaction(TransactionDto transactionRequest) {
        final String senderNumber = transactionRequest.getSenderNumber();
        final String receiverNumber = transactionRequest.getReceiverNumber();
        final double amount = transactionRequest.getAmount();
        if (senderNumber == null || receiverNumber == null) {
            log.debug("Invalid transaction");
            throw new IllegalArgumentException("Invalid transaction");
        }
        if (senderNumber.equals(receiverNumber)) {
            log.debug("Sender and receiver account must be different");
            throw new IllegalArgumentException("Sender and receiver account must be different");
        }

        final Account sender = accountRepository.findByNumber(senderNumber)
                .orElseThrow(() -> new ApiException("Sender account with number " + senderNumber + " does not exist", HttpStatus.NOT_FOUND));
        final double senderNewBalance = sender.getBalance() - amount;
        if (senderNewBalance < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        final Account receiver = accountRepository.findByNumber(receiverNumber)
                .orElseThrow(() -> new ApiException("Receiver account with number " + receiverNumber + " does not exist", HttpStatus.NOT_FOUND));

        final double receiverNewBalance = receiver.getBalance() + amount;
        final boolean isWithdrawalSuccess = updateBalance(senderNumber, senderNewBalance);
        final boolean isDepositSuccess = updateBalance(receiverNumber, receiverNewBalance);

        final Transaction transaction = initTransaction(Transaction.TYPE.TRANSFER);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setRemarks(transactionRequest.getRemarks());
        transaction.setStatus(Transaction.STATUS.FAILED);
        if (isWithdrawalSuccess && isDepositSuccess) {
            transaction.setStatus(Transaction.STATUS.SUCCESS);
        }
        final Transaction savedTransaction = transactionRepository.save(transaction);
        return TransactionMapper.INSTANCE.convertToTransactionDto(savedTransaction);
    }

    Transaction initTransaction(String type) {
        final var currentTime = Instant.now();
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(currentTime);
        transaction.setType(type);
        return transaction;
    }

    boolean updateBalance(String accountNumber, double balance) {
        final AccountDto accountDto = new AccountDto();
        accountDto.setBalance(balance);
        final ResponseEntity<ApiExceptionResponse<AccountDto>> response = accountCommandService.updateBalance(accountNumber, accountDto);
        return response.getStatusCode().value() == 200;
    }

    // TODO: withdrawal balance
    TransactionDto createWithdrawalTransaction(TransactionDto transactionRequest) {
        return TransactionMapper.INSTANCE.convertToTransactionDto(initTransaction(Transaction.TYPE.WITHDRAWAL));
    }

    // TODO: deposit balance
    TransactionDto createDepositTransaction(TransactionDto transactionRequest) {
        return TransactionMapper.INSTANCE.convertToTransactionDto(initTransaction(Transaction.TYPE.DEPOSIT));
    }
}
