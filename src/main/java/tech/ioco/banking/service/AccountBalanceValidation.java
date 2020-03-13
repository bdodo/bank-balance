package tech.ioco.banking.service;

import tech.ioco.banking.model.ClientAccount;

import java.math.BigDecimal;
import java.util.function.BiFunction;

import static tech.ioco.banking.utils.BankingConstants.CHEQUE_ACCOUNT_CODE;
import static tech.ioco.banking.utils.BankingConstants.MAX_CHEQUE_WITHDRAWAL;

public interface AccountBalanceValidation extends BiFunction<ClientAccount, BigDecimal, Boolean> {
    static AccountBalanceValidation hasEnoughPositiveBalance() {
        return (clientAccount, withdrawalAmount) ->
                clientAccount.getDisplayBalance().compareTo(BigDecimal.ZERO) > 0 &&
                        clientAccount.getDisplayBalance().compareTo(withdrawalAmount) > 0;
    }

    static AccountBalanceValidation amountCanBeWithdrawnFromChequeAccount() {
        return (clientAccount, withdrawalAmount) ->
                clientAccount.getAccountType().getAccountTypeCode().equalsIgnoreCase(CHEQUE_ACCOUNT_CODE) &&
                        withdrawalAmount.compareTo(MAX_CHEQUE_WITHDRAWAL) < 0;

    }

    default AccountBalanceValidation or(AccountBalanceValidation other) {
        return (clientAccount, withdrawalAmount) -> {
            Boolean canWithdraw = this.apply(clientAccount, withdrawalAmount);
            return canWithdraw.equals(false) ? other.apply(clientAccount, withdrawalAmount) : canWithdraw;
        };

    }
}
