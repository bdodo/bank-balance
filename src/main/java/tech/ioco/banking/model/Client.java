package tech.ioco.banking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class Client {
    private int clientId;
    private String title;
    private String name;
    private String surname;
    private Date dob;
    private ClientSubType clientSubType;
}
