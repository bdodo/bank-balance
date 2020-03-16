package tech.ioco.banking.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tech.ioco.banking.exception.ClientAccountException;
import tech.ioco.banking.mapper.ClientAccountMapper;
import tech.ioco.banking.model.AccountType;
import tech.ioco.banking.model.ClientAccount;
import tech.ioco.banking.model.Currency;
import tech.ioco.banking.model.CurrencyConversionRate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static tech.ioco.banking.utils.BankingConstants.NO_ACCOUNTS_MSG;

@SpringBootTest(classes = AccountService.class)
class AccountServiceTest {
    @Autowired
    private AccountService accountService;

    @MockBean
    private ClientAccountMapper clientAccountMapper;

    private ClientAccount mockAccount1, mockAccount2, mockAccount3, mockAccount4;

    private List<ClientAccount> clientAccounts;

    @BeforeEach
    void setUp() {
        var mockAccountType = new AccountType("CHQ", "Cheque Account", true);
        var mockAccountType2 = new AccountType("LN", "Loan Account", false);
        var mockCurrency = new Currency("R", "Rand", 2, new CurrencyConversionRate(null, "*", BigDecimal.valueOf(0.8)));
        mockAccount1 = new ClientAccount(1, "12345", mockAccountType, mockCurrency, BigDecimal.valueOf(2000));
        mockAccount2 = new ClientAccount(2, "12346", mockAccountType, mockCurrency, BigDecimal.valueOf(6100));
        mockAccount3 = new ClientAccount(3, "12347", mockAccountType2, mockCurrency, BigDecimal.valueOf(5000));
        mockAccount4 = new ClientAccount(4, "12348", mockAccountType, mockCurrency, BigDecimal.valueOf(1760));

        clientAccounts = List.of(mockAccount1, mockAccount2, mockAccount3, mockAccount4);
        when(clientAccountMapper.findAllByClientId(anyInt())).thenReturn(clientAccounts);
    }

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
        when(clientAccountMapper.findAllByClientId(anyInt())).thenReturn(List.of());
        assertThrows(ClientAccountException.class, ()->accountService.getSortedTransactionalAccountsByClientId(100), NO_ACCOUNTS_MSG);
    }

    @Test
    void givenClientId_whenRecordsExistsInDatabase_thenReturnsCurrencyAccountsOnly() {
        var accounts = accountService.getSortedCurrencyAccountsByClientId(1);

        assertAll(
                () -> assertNotNull(accounts),
                () -> assertEquals(4, accounts.size())
        );

        accounts.forEach(account -> assertFalse(account.getCurrency().equalsIgnoreCase("ZAR")));
    }

    @Test
    void givenClientId_whenRecordsExistsInDatabase_thenReturnsCurrencyAccountsWithRoundedAmounts() {
        var accounts = accountService.getSortedCurrencyAccountsByClientId(1);

        assertAll(
                () -> assertNotNull(accounts),
                () -> assertEquals(4, accounts.size())
        );

        accounts.forEach(account -> assertEquals(2, account.getRandAmount().scale()));
    }

    @Test
    void givenClientId_whenRecordsExistsInDatabase_thenReturnsCurrencyAccountsWithSortedCorrectAmounts() {
        var accounts = accountService.getSortedCurrencyAccountsByClientId(1);

        assertAll(
                () -> assertNotNull(accounts),
                () -> assertEquals(4, accounts.size()),
                ()-> assertEquals(BigDecimal.valueOf(4880).setScale(2), accounts.get(0).getRandAmount()),
                ()-> assertEquals(BigDecimal.valueOf(4000).setScale(2), accounts.get(1).getRandAmount()),
                ()-> assertEquals(BigDecimal.valueOf(1600).setScale(2), accounts.get(2).getRandAmount())
        );
    }

    @Test
    void givenRecordsDoNotExistsInDatabase_whenGetCurrencyAccounts_thenThrowsClientAccountException(){
        when(clientAccountMapper.findAllByClientId(anyInt())).thenReturn(List.of());
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