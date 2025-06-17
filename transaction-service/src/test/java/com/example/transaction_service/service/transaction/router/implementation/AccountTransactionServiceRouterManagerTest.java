package com.example.transaction_service.service.transaction.router.implementation;

import com.example.transaction_service.model.account.type.enumeration.AccountTypeEnumeration;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.service.transaction.AbstractCreditAccountTransactionService;
import com.example.transaction_service.service.transaction.AbstractDebitAccountTransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountTransactionServiceRouterManagerTest {
    @Mock
    private AbstractDebitAccountTransactionService<Transaction> debitAccountTransactionService;
    @Mock
    private AbstractCreditAccountTransactionService<Transaction> creditAccountTransactionService;
    @InjectMocks
    private AccountTransactionServiceRouterManager accountTransactionServiceRouterManager;

    @Test
    public void getByAccountTypeEnumeration_getCreditAccountTransactionServiceManager_success(){
        Assertions.assertEquals(creditAccountTransactionService.getClass(), accountTransactionServiceRouterManager.getByAccountTypeEnumeration(AccountTypeEnumeration.CREDIT).getClass());
    }

    @Test
    public void getByAccountTypeEnumeration_getDebitAccountTransactionServiceManager_success(){
        Assertions.assertEquals(debitAccountTransactionService.getClass(), accountTransactionServiceRouterManager.getByAccountTypeEnumeration(AccountTypeEnumeration.DEBIT).getClass());
    }
}
