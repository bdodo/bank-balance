package tech.ioco.banking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DenominationType {
    private char denominationTypeCode;
    private String description;
}
