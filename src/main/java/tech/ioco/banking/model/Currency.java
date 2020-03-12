package tech.ioco.banking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Currency {
    private String currencyCode;
    private String description;
    private int decimalPlaces;
}
