package tech.ioco.banking.service;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.ioco.banking.exception.ClientAccountException;
import tech.ioco.banking.model.Currency;
import tech.ioco.banking.model.CurrencyConversionRate;

import java.math.BigDecimal;
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

    @Test
    void givenClientId_whenRecordsExistsInDatabase_thenReturnsCurrencyAccountsOnly() {
        var accounts = accountService.getSortedCurrencyAccountsByClientId(1);

        assertAll(
                () -> assertNotNull(accounts),
                () -> assertEquals(3, accounts.size())
        );

        accounts.forEach(account -> assertFalse(account.getCurrency().equalsIgnoreCase("ZAR")));
    }

    @Test
    void givenClientId_whenRecordsExistsInDatabase_thenReturnsCurrencyAccountsWithRoundedAmounts() {
        var accounts = accountService.getSortedCurrencyAccountsByClientId(1);

        assertAll(
                () -> assertNotNull(accounts),
                () -> assertEquals(3, accounts.size())
        );

        accounts.forEach(account -> assertEquals(2, account.getRandAmount().scale()));
    }

    @Test
    void givenClientId_whenRecordsExistsInDatabase_thenReturnsCurrencyAccountsWithSortedCorrectAmounts() {
        var accounts = accountService.getSortedCurrencyAccountsByClientId(1);

        assertAll(
                () -> assertNotNull(accounts),
                () -> assertEquals(3, accounts.size()),
                ()-> assertEquals(BigDecimal.valueOf(638087.98), accounts.get(0).getRandAmount()),
                ()-> assertEquals(BigDecimal.valueOf(417489.21), accounts.get(1).getRandAmount()),
                ()-> assertEquals(BigDecimal.valueOf(367665.08), accounts.get(2).getRandAmount())
        );
    }

    @Test
    void givenRecordsDoNotExistsInDatabase_whenGetCurrencyAccounts_thenThrowsClientAccountException(){
        assertThrows(ClientAccountException.class, ()->accountService.getSortedCurrencyAccountsByClientId(100), NO_ACCOUNTS_MSG);
    }

    @Test
    public void givenMultiplyIndicator_whenCalculateRandAmount(){
        var conversionRate = new CurrencyConversionRate();
        conversionRate.setConversionIndicator("*");
        conversionRate.setRate(BigDecimal.valueOf(0.8));
        var mockCurrency = new Currency();
        mockCurrency.setCurrencyConversionRate(conversionRate);
        mockCurrency.setDecimalPlaces(2);

        var randAmount = accountService.calculateRandAmount(BigDecimal.TEN, mockCurrency);
        assertEquals(BigDecimal.valueOf(8.0).setScale(2), randAmount);
    }

    @Test
    public void givenDivideIndicator_whenCalculateRandAmount(){
        var conversionRate = new CurrencyConversionRate();
        conversionRate.setConversionIndicator("/");
        conversionRate.setRate(BigDecimal.valueOf(0.8));
        var mockCurrency = new Currency();
        mockCurrency.setDecimalPlaces(2);
        mockCurrency.setCurrencyConversionRate(conversionRate);

        var randAmount = accountService.calculateRandAmount(BigDecimal.TEN, mockCurrency);
        assertEquals(BigDecimal.valueOf(12.5).setScale(2), randAmount);
    }
}