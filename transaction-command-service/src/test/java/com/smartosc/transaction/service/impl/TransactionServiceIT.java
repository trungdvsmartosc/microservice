package com.smartosc.transaction.service.impl;

import com.smartosc.transaction.base.TestBase;
import com.smartosc.transaction.model.Account;
import com.smartosc.transaction.model.Transaction;
import com.smartosc.transaction.model.TransactionRequest;
import com.smartosc.transaction.repository.AccountRepository;
import com.smartosc.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Import;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@Import({TestBase.class})
class TransactionServiceIT {


    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;


    private Account getSenderAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(1000);
        return account;
    }

    private Account getReceiverAccount() {
        Account account = new Account();
        account.setId(2L);
        account.setBalance(2000);
        return account;
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getTransactionsByUserIdTest() {
        Transaction transaction_1 = new Transaction();
        transaction_1.setId(1L);
        transaction_1.setSender(getSenderAccount());
        transaction_1.setReceiver(getReceiverAccount());
        transaction_1.setAmount(10L);
        transaction_1.setStatus(Transaction.STATUS.PENDING);
        transaction_1.setRemarks("transaction 1");
        transaction_1.setTransactionDate(Instant.now());

        Transaction transaction_2 = new Transaction();
        transaction_2.setId(2L);
        transaction_2.setSender(getSenderAccount());
        transaction_2.setReceiver(new Account());
        transaction_2.setAmount(20L);
        transaction_2.setStatus(Transaction.STATUS.PENDING);
        transaction_2.setRemarks("transaction 2");
        transaction_2.setTransactionDate(Instant.now());

        List<Transaction> transactions = List.of(transaction_1, transaction_2);

        Long userId = getSenderAccount().getId();
        when(transactionRepository.findTransactionsBySenderAccountOrReceiverAccountId(userId)).thenReturn(transactions);

        List<Transaction> result = transactionService.getTransactionsByUserId(userId);

        assertThat(result).hasSize(2);
        assertThat(result).extracting(transaction -> transaction.getSender().getId()).containsExactly(userId, userId);
        verify(transactionRepository, times(1)).findTransactionsBySenderAccountOrReceiverAccountId(userId);
    }

    @Test
    void testValidateTransaction_Invalid() {
//        RuntimeException exception1 = assertThrows(RuntimeException.class, () -> transactionService.validateTransaction(1L, null));
//        assertEquals("Invalid transaction", exception1.getMessage());
//
//        RuntimeException exception2 = assertThrows(RuntimeException.class, () -> transactionService.validateTransaction(1L, null));
//        assertEquals("Invalid transaction", exception2.getMessage());
    }

    @Test
    void testBuildTransaction() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setSenderId(1L);
        transactionRequest.setReceiverId(1L);
        transactionRequest.setAmount(100.5);
        transactionRequest.setRemarks("Test build transaction");
        Transaction transaction = transactionService.buildTransferTransaction(transactionRequest, getSenderAccount(), getReceiverAccount());

        assert transaction.getSender().equals(getSenderAccount());
        assertEquals(transaction.getReceiver(), getReceiverAccount());
        assertEquals(transaction.getStatus(), Transaction.STATUS.PENDING);
        assertEquals(transaction.getAmount(), transactionRequest.getAmount());
        assertEquals(transaction.getRemarks(), transactionRequest.getRemarks());
    }


    @Test
    void testCreateTransaction_Transfer_AccountDoesNotExist() {
    }

    @Test
    void testValidateTransaction_SameSenderAndReceiverAccount() {
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> transactionService.validateTransaction(1L, 1L));
//        assertEquals("Account with id 1 must not be the same", exception.getMessage());
    }

    @Test
    void testTransactionProcessor() {
        String testSuccessMsg = "Test success";
        double amount = 10.5;
//        TransactionRequest transactionRequest = new TransactionRequest(1L, 2L, amount, testSuccessMsg);

        double senderBalance = getSenderAccount().getBalance() - amount;
        double receiverBalance = getReceiverAccount().getBalance() + amount;

        Account sender = getSenderAccount();
        sender.setBalance(senderBalance);

        Account receiver = getReceiverAccount();
        receiver.setBalance(receiverBalance);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(getSenderAccount()));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(getReceiverAccount()));


        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

//        Transaction transaction = transactionService.transactionProcessor(transactionRequest, getSenderAccount().getId(), getReceiverAccount().getId());

//        assert transaction != null;
//        assert transaction.getAmount() == amount;
//        assert transaction.getStatus().equals(Transaction.STATUS.PROCESSING);
//        assert transaction.getRemarks().equals(testSuccessMsg);
//
//        assert transaction.getSender() != null;
//        assert Objects.equals(transaction.getSender().getId(), getSenderAccount().getId());
//        assert Objects.equals(transaction.getSender().getBalance(), getSenderAccount().getBalance() - amount);
//
//        assert transaction.getReceiver() != null;
//        assert Objects.equals(transaction.getReceiver().getId(), getReceiverAccount().getId());
//        assert Objects.equals(transaction.getReceiver().getBalance(), getReceiverAccount().getBalance() + amount);
    }

    @Test
    void testWithdraw_Insufficient() {
        final Long userId = getSenderAccount().getId();
        final double amount = 10000;
        when(accountRepository.findById(userId)).thenReturn(Optional.of(getSenderAccount()));

    }

    @Test
    void testWithdraw() {
        final Long userId = getSenderAccount().getId();
        final double amount = 10;
        when(accountRepository.findById(userId)).thenReturn(Optional.of(getSenderAccount()));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

    }

    @Test
    void testDeposit() {
        final Long userId = getReceiverAccount().getId();
        final double amount = 10;
        when(accountRepository.findById(userId)).thenReturn(Optional.of(getReceiverAccount()));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

    }
}
