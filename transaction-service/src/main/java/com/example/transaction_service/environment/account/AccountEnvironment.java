package com.example.transaction_service.environment.account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Класс, хранящий переменные окружения, связанные с клиентскими аккаунтами
 */
@Component
public class AccountEnvironment {
    private final double ACCOUNT_CREDIT_START_BALANCE;
    private final double ACCOUNT_TRANSACTION_MAX_AMOUNT;
    private final double ACCOUNT_TRANSACTION_MIN_AMOUNT;
    private final double ACCOUNT_BALANCE_MAX_AMOUNT;
    private final int ACCOUNT_ARRESTED_MAX_SELECT_COUNT;

    public AccountEnvironment(@Value("${account.credit.start-balance}") double ACCOUNT_CREDIT_START_BALANCE, @Value("${account.transaction.amount.min}") double ACCOUNT_TRANSACTION_MIN_AMOUNT, @Value("${account.transaction.amount.max}") double ACCOUNT_TRANSACTION_MAX_AMOUNT, @Value("${account.balance.max}") double ACCOUNT_BALANCE_MAX_AMOUNT, @Value("${account.arrested.max-select-count}") int ACCOUNT_ARRESTED_MAX_SELECT_COUNT) {
        this.ACCOUNT_CREDIT_START_BALANCE = ACCOUNT_CREDIT_START_BALANCE;
        this.ACCOUNT_TRANSACTION_MIN_AMOUNT = ACCOUNT_TRANSACTION_MIN_AMOUNT;
        this.ACCOUNT_TRANSACTION_MAX_AMOUNT = ACCOUNT_TRANSACTION_MAX_AMOUNT;
        this.ACCOUNT_BALANCE_MAX_AMOUNT = ACCOUNT_BALANCE_MAX_AMOUNT;
        this.ACCOUNT_ARRESTED_MAX_SELECT_COUNT = ACCOUNT_ARRESTED_MAX_SELECT_COUNT;
    }

    public double getACCOUNT_CREDIT_START_BALANCE() {
        return ACCOUNT_CREDIT_START_BALANCE;
    }

    public double getACCOUNT_TRANSACTION_MAX_AMOUNT() {
        return ACCOUNT_TRANSACTION_MAX_AMOUNT;
    }

    public double getACCOUNT_TRANSACTION_MIN_AMOUNT() {
        return ACCOUNT_TRANSACTION_MIN_AMOUNT;
    }

    public double getACCOUNT_BALANCE_MAX_AMOUNT() {
        return ACCOUNT_BALANCE_MAX_AMOUNT;
    }

    public int getACCOUNT_ARRESTED_MAX_SELECT_COUNT() {
        return ACCOUNT_ARRESTED_MAX_SELECT_COUNT;
    }
}
