package com.smartosc.transaction.service.impl;

import com.smartosc.transaction.exception.ApiException;
import com.smartosc.transaction.external.AccountService;
import com.smartosc.transaction.model.dto.AccountDto;
import com.smartosc.transaction.model.entity.Account;
import com.smartosc.transaction.model.entity.Transaction;
import com.smartosc.transaction.model.entity.TransactionRequest;
import com.smartosc.transaction.repository.TransactionRepository;
import com.smartosc.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Override
    public List<Transaction> getTransactionsByUserId(final Long id) {
        return transactionRepository.findTransactionsBySenderAccountOrReceiverAccountId(id);
    }

    @Override
    public Transaction createTransaction(TransactionRequest transactionRequest) {
        if (StringUtils.isEmpty(transactionRequest.getType()) || transactionRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("Invalid transaction");
        }

        if (transactionRequest.getType().equals(Transaction.TYPE.TRANSFER)) {
            return createTransferTransaction(transactionRequest);
        }

        if (transactionRequest.getType().equals(Transaction.TYPE.WITHDRAWAL)) {
            return createWithdrawalTransaction(transactionRequest);
        }

        if (transactionRequest.getType().equals(Transaction.TYPE.DEPOSIT)) {
            return createDepositTransaction(transactionRequest);
        }
        throw new IllegalArgumentException("Invalid transaction");
    }

    Transaction createTransferTransaction(TransactionRequest transactionRequest) {
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

        final Account sender = accountService.findByAccountNumber(senderNumber).getBody();
        if (sender == null) {
            throw new ApiException("Sender account with number " + senderNumber + " does not exist", HttpStatus.NOT_FOUND);
        }
        final double senderNewBalance = sender.getBalance() - amount;
        if (senderNewBalance < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        final Account receiver = accountService.findByAccountNumber(receiverNumber).getBody();
        if (receiver == null) {
            throw new ApiException("Receiver account with number " + receiverNumber + " does not exist", HttpStatus.NOT_FOUND);
        }
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
        return transactionRepository.save(transaction);
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
        final ResponseEntity<Account> response = accountService.updateAccount(accountNumber, accountDto);
        return response.getStatusCode().value() == 200;
    }

    // TODO: withdrawal balance
    Transaction createWithdrawalTransaction(TransactionRequest transactionRequest) {
        return initTransaction(Transaction.TYPE.WITHDRAWAL);
    }

    // TODO: deposit balance
    Transaction createDepositTransaction(TransactionRequest transactionRequest) {
        return initTransaction(Transaction.TYPE.DEPOSIT);
    }
}
