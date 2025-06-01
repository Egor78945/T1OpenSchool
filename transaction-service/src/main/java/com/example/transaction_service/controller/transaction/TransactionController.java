package com.example.transaction_service.controller.transaction;

import com.example.transaction_service.controller.common.advice.handler.CommonControllerExceptionHandler;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.status.entity.TransactionStatus;
import com.example.transaction_service.model.transaction.status.enumeration.TransactionStatusEnumeration;
import com.example.transaction_service.model.transaction.type.enumeration.TransactionTypeEnumeration;
import com.example.transaction_service.service.account.AbstractAccountService;
import com.example.transaction_service.service.transaction.builder.AbstractAccountTransactionBuilderService;
import com.example.transaction_service.service.transaction.kafka.AbstractKafkaAccountTransactionService;
import com.example.transaction_service.service.transaction.status.TransactionStatusService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Контроллер, принимающий запросы, связанные с транзакциями клиентских аккаунтов
 */
@RestController
@RequestMapping("/api/v1/transaction")
@CommonControllerExceptionHandler
public class TransactionController {
    private final AbstractAccountService<Account> accountService;
    private final AbstractKafkaAccountTransactionService<String, Transaction> kafkaAccountTransactionService;
    private final AbstractAccountTransactionBuilderService<Transaction, Account> accountTransactionBuilderService;
    private final TransactionStatusService<TransactionStatus> transactionStatusService;

    public TransactionController(@Qualifier("kafkaAccountTransactionServiceManager") AbstractKafkaAccountTransactionService<String, Transaction> kafkaAccountTransactionService, @Qualifier("accountTransactionBuilderServiceManager") AbstractAccountTransactionBuilderService<Transaction, Account> accountTransactionBuilderService, @Qualifier("debitAccountServiceManager") AbstractAccountService<Account> accountService, @Qualifier("transactionStatusServiceManager") TransactionStatusService<TransactionStatus> transactionStatusService) {
        this.kafkaAccountTransactionService = kafkaAccountTransactionService;
        this.accountTransactionBuilderService = accountTransactionBuilderService;
        this.accountService = accountService;
        this.transactionStatusService = transactionStatusService;
    }

    /**
     * Метод, выполняющий внесение денег на существующий клиентский аккаунт
     * @param recipientId Id существующего получателя {@link Account}
     * @param amount Сумма вносимых денег
     * @return Текущий баланс клиентского аккаунта после внесения денег
     */
    @PutMapping("/insert")
    public ResponseEntity<String> insertMoney(@RequestParam("recipientId") String recipientId, @RequestParam("amount") double amount) {
        kafkaAccountTransactionService.save(TransactionTypeEnumeration.INSERT.name(), accountTransactionBuilderService.buildInsert(accountService.getByAccountId(UUID.fromString(recipientId)), amount, transactionStatusService.getById(TransactionStatusEnumeration.REQUESTED.getId())));
        return ResponseEntity.ok("transaction is being processed");
    }

    /**
     * Метод, выполняющий перевод денег с одного существующешл клиентского аккаунта на другой
     * @param senderId Id отправителя {@link Account}
     * @param recipientId Id получателя {@link Account}
     * @param amount Сумма переводимых денег
     * @return Текущий баланс клиентского аккаунта после выполнения перевода
     */
    @PutMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestParam("senderId") String senderId, @RequestParam("recipientId") String recipientId, @RequestParam("amount") double amount) {
        kafkaAccountTransactionService.save(TransactionTypeEnumeration.TRANSFER.name(), accountTransactionBuilderService.buildTransfer(accountService.getByAccountId(UUID.fromString(senderId)), accountService.getByAccountId(UUID.fromString(recipientId)), amount, transactionStatusService.getById(TransactionStatusEnumeration.REQUESTED.getId())));
        return ResponseEntity.ok("transaction is being processed");
    }
}
