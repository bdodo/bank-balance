package tech.ioco.banking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountType {
    private String accountTypeCode;
    private String description;
    private boolean transactional;
}
