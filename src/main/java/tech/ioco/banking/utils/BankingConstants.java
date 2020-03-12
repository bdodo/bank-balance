package tech.ioco.banking.utils;

import java.math.BigDecimal;

public class BankingConstants {
    private BankingConstants(){}
    public static final String NO_ACCOUNTS_MSG = "No accounts to display";
    public static final String CHEQUE_ACCOUNT_CODE = "CHQ";
    public static final BigDecimal MAX_CHEQUE_WITHDRAWAL = BigDecimal.valueOf(10000);
}
