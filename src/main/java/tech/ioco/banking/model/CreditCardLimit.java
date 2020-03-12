package tech.ioco.banking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CreditCardLimit {
    private ClientAccount clientAccount;
    private BigDecimal accountLimit;
}
