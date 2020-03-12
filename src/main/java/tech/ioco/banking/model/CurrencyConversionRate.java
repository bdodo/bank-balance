package tech.ioco.banking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CurrencyConversionRate {
    private Currency currency;
    private String conversionIndicator;
    private BigDecimal rate;
}
