package tech.ioco.banking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientSubType {
private String clientSubTypeCode;
private ClientType clientType;
private String description;
}
