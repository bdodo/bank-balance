package tech.ioco.banking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AtmAllocation {
    private int atmAllocationId;
    private Atm atm;
    private Denomination denomination;
    private int count;
}
