package tech.ioco.banking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Atm {
    private int atmId;
    private String name;
    private String location;
}
