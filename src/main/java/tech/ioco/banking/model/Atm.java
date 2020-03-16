package tech.ioco.banking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Atm {
    private int atmId;
    private String name;
    private String location;
}
