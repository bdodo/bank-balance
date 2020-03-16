package tech.ioco.banking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientAccount {
    private int clientId;
    private String clientAccountNumber;
    private AccountType accountType;
    private Currency currency;
    private BigDecimal displayBalance;
}
