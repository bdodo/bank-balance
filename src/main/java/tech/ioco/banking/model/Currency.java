package tech.ioco.banking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    private String currencyCode;
    private String description;
    private int decimalPlaces;
    private CurrencyConversionRate currencyConversionRate;
}
