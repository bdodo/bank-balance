package tech.ioco.banking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.ioco.banking.model.Atm;
import tech.ioco.banking.model.AtmAllocation;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtmAllocationDto {
    private Atm atm;
    private int noteValue;
    private int noteCount;

    public AtmAllocationDto(AtmAllocation allocation){
        this.atm = allocation.getAtm();
        noteValue = allocation.getDenomination().getValue().intValue();
        noteCount = allocation.getCount();
    }
}
