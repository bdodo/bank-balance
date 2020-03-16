package tech.ioco.banking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtmAllocation {
    private int atmAllocationId;
    private Atm atm;
    private Denomination denomination;
    private int count;
}
