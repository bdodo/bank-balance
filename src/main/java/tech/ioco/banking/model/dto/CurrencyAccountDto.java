package tech.ioco.banking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyAccountDto {
    private String accountNumber;
    private String currency;
    private BigDecimal currencyBalance;
    private BigDecimal conversionRate;
    private BigDecimal randAmount;
}
