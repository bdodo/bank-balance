package tech.ioco.banking.service;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.ioco.banking.exception.ClientAccountException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tech.ioco.banking.utils.BankingConstants.NO_ACCOUNTS_MSG;

@SpringBootTest
@AutoConfigureMybatis
class AccountServiceTest {
    @Autowired
    private AccountService accountService;

    @Test
    void givenClientId_whenRecordsExistsInDatabase_thenReturnsTransactionalAccountsOnly() {
        var accounts = accountService.getSortedTransactionalAccountsByClientId(1);
        var transactionalAccountTypes = List.of("Credit Card", "Savings Account", "Cheque Account");
        assertAll(
                () -> assertNotNull(accounts),
                () -> assertEquals(3, accounts.size())
        );

        accounts.forEach(account -> assertTrue(transactionalAccountTypes.contains(account.getAccountType())));
    }

    @Test
    void givenClientId_whenRecordsExistsInDatabase_thenReturnsSortedTransactionalAccounts(){
        var accounts = accountService.getSortedTransactionalAccountsByClientId(1);
        assertEquals(3, accounts.size());
        assertEquals(1, accounts.get(0).getAccountBalance().compareTo(accounts.get(1).getAccountBalance()));
        assertEquals(1, accounts.get(1).getAccountBalance().compareTo(accounts.get(2).getAccountBalance()));
    }

    @Test
    void givenClientId_whenRecordsExistsInDatabase_thenReturnsTransactionalAccountsWithBalancesTo2DecimalPlaces(){
        var accounts = accountService.getSortedTransactionalAccountsByClientId(1);
        assertEquals(3, accounts.size());
        assertEquals(2, accounts.get(0).getAccountBalance().scale());
    }

    @Test
    void givenClientId_whenRecordsDoNotExistsInDatabase_thenThrowsClientAccountException(){
        assertThrows(ClientAccountException.class, ()->accountService.getSortedTransactionalAccountsByClientId(100), NO_ACCOUNTS_MSG);
    }
}