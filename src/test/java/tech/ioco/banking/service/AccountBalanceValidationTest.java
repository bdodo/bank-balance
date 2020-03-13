package tech.ioco.banking.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.ioco.banking.model.AccountType;
import tech.ioco.banking.model.ClientAccount;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static tech.ioco.banking.service.AccountBalanceValidation.amountCanBeWithdrawnFromChequeAccount;
import static tech.ioco.banking.utils.BankingConstants.CHEQUE_ACCOUNT_CODE;

class AccountBalanceValidationTest {
    private ClientAccount clientAccount;
    private BigDecimal withdrawalAmount;
    private AccountType accountType;

    @BeforeEach
    void setUp() {
        clientAccount = new ClientAccount();
        clientAccount.setDisplayBalance(BigDecimal.TEN);

        withdrawalAmount = BigDecimal.ONE;

        accountType = new AccountType();
        accountType.setAccountTypeCode(CHEQUE_ACCOUNT_CODE);
    }

    @Test
    void hasEnoughPositiveBalance_returnsTrue() {
        assertTrue(AccountBalanceValidation.hasEnoughPositiveBalance().apply(clientAccount, withdrawalAmount));
    }

    @Test
    void hasNegativeBalance_returnsFalse() {
        clientAccount.setDisplayBalance(BigDecimal.valueOf(-10));
        assertFalse(AccountBalanceValidation.hasEnoughPositiveBalance().apply(clientAccount, withdrawalAmount));
    }

    @Test
    void hasPositiveBalanceButNotEnoughToWithdraw_returnsFalse() {
        clientAccount.setDisplayBalance(BigDecimal.ONE);
        withdrawalAmount = BigDecimal.TEN;
        assertFalse(AccountBalanceValidation.hasEnoughPositiveBalance().apply(clientAccount, withdrawalAmount));
    }

    @Test
    void amountCanBeWithdrawnFromChequeAccount_returnsTrue() {
        clientAccount.setAccountType(accountType);
        assertTrue(amountCanBeWithdrawnFromChequeAccount().apply(clientAccount, withdrawalAmount));
    }

    @Test
    void givenAccountIsNotCheque_amountCanBeWithdrawnFromChequeAccount_returnsFalse() {
        accountType.setAccountTypeCode("SVG");
        clientAccount.setAccountType(accountType);
        assertFalse(amountCanBeWithdrawnFromChequeAccount().apply(clientAccount, withdrawalAmount));
    }

    @Test
    void givenWithdrawalAmountWithinLimit_amountCanBeWithdrawnFromChequeAccount_returnsTrue() {
        clientAccount.setAccountType(accountType);
        withdrawalAmount = BigDecimal.valueOf(9999);
        assertTrue(amountCanBeWithdrawnFromChequeAccount().apply(clientAccount, withdrawalAmount));
    }

    @Test
    void givenWithdrawalAmountExceedsLimit_amountCanBeWithdrawnFromChequeAccount_returnsFalse() {
        clientAccount.setAccountType(accountType);
        withdrawalAmount = BigDecimal.valueOf(10001);
        assertFalse(amountCanBeWithdrawnFromChequeAccount().apply(clientAccount, withdrawalAmount));
    }

    @Test
    void givenEnoughPositiveBalance_or_returnsTrue() {
        assertTrue(AccountBalanceValidation.hasEnoughPositiveBalance().or(amountCanBeWithdrawnFromChequeAccount()).apply(clientAccount, withdrawalAmount));
    }

    @Test
    void givenNegativeBalanceAndCheque_or_returnsTrue() {
        clientAccount.setDisplayBalance(BigDecimal.valueOf(-10));
        clientAccount.setAccountType(accountType);
        assertTrue(AccountBalanceValidation.hasEnoughPositiveBalance().or(amountCanBeWithdrawnFromChequeAccount()).apply(clientAccount, withdrawalAmount));
    }
}