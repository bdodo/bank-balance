package tech.ioco.banking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class TransactionalAccountDto {
    private String accountNumber;
    private String accountType;
    private BigDecimal accountBalance;
}
