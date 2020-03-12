package tech.ioco.banking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountType {
    private String accountTypeCode;
    private String description;
    private boolean transactional;
}
