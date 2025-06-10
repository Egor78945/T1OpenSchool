package com.example.transaction_service.service.transaction.builder;

import com.example.transaction_service.exception.ProcessingException;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.status.entity.TransactionStatus;
import com.example.transaction_service.model.transaction.type.entity.TransactionType;
import com.example.transaction_service.service.transaction.AbstractAccountTransactionService;
import com.example.transaction_service.service.transaction.type.TransactionTypeService;

import java.util.UUID;

/**
 * Абстрактный класс, предоставляющий баззовый функционал для моделирования {@link Transaction}
 * @param <T> Тип, представляющий {@link Transaction} или его наследника
 * @param <A> Тип, представляющий {@link Account} или его наследника
 */
public abstract class AbstractAccountTransactionBuilderService<T extends Transaction, A extends Account> {
    protected final TransactionTypeService<TransactionType> transactionTypeService;

    public AbstractAccountTransactionBuilderService(TransactionTypeService<TransactionType> transactionTypeService) {
        this.transactionTypeService = transactionTypeService;
    }

    /**
     * Смоделировать {@link Transaction} тип <b>INSERT</b>
     * @param recipient Получатель транзакции {@link Account}
     * @param amount Сумма транзакции
     * @param transactionStatus Статус транзакции
     * @return {@link Transaction} или его наследник
     */
    public abstract T buildInsert(A recipient, double amount, TransactionStatus transactionStatus);
    /**
     * Смоделировать {@link Transaction} тип <b>TRANSFER</b>
     * @param sender Отправитель транзакции {@link Account}
     * @param recipient Получатель транзакции {@link Account}
     * @param amount Сумма транзакции
     * @param transactionStatus Статус транзакции
     * @return {@link Transaction} или его наследник
     */
    public abstract T buildTransfer(A sender, A recipient, double amount, TransactionStatus transactionStatus);
}
