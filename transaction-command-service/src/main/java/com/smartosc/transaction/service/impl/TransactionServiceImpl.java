package com.smartosc.transaction.service.impl;

import com.smartosc.transaction.model.Account;
import com.smartosc.transaction.model.Transaction;
import com.smartosc.transaction.model.TransactionRequest;
import com.smartosc.transaction.repository.AccountRepository;
import com.smartosc.transaction.repository.TransactionRepository;
import com.smartosc.transaction.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<Transaction> getTransactionsByUserId(final Long id) {
        return transactionRepository.findTransactionsBySenderAccountOrReceiverAccountId(id);
    }

    @Override
    public Transaction createTransactionTransfer(TransactionRequest transactionRequest) {
        validateTransferTransaction(transactionRequest);
        final Transaction transaction = buildTransferTransaction(transactionRequest);
        return transactionRepository.save(transaction);
    }

    void validateTransferTransaction(TransactionRequest transactionRequest) {
        final Long senderAccountId = transactionRequest.getSenderId();
        final Long receiverAccountId = transactionRequest.getReceiverId();
        if (senderAccountId == null || receiverAccountId == null) {
            log.debug("Invalid transaction");
            throw new IllegalArgumentException("Invalid transaction");
        }
        if (Objects.equals(senderAccountId, receiverAccountId)) {
            log.debug("Account with id {} must not be the same", senderAccountId);
            throw new IllegalArgumentException("Account with id " + senderAccountId + " must not be the same");
        }
    }

    Transaction buildTransferTransaction(TransactionRequest transactionRequest) {
        final var currentTime = Instant.now();
        final Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setRemarks(transactionRequest.getRemarks());
        transaction.setStatus(Transaction.STATUS.PENDING);
        transaction.setTransactionDate(currentTime);
        transaction.setType(Transaction.TYPE.TRANSFER);
        return transaction;
    }

}
