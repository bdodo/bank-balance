package tech.ioco.banking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Denomination {
    private int denominationId;
    private BigDecimal value;
    private DenominationType denominationType;
}
