package nl.rabobank.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rabobank.authorizations.Authorization;

@Getter
@Setter
@NoArgsConstructor
public class PowerOfAttorneyRequest {
    private String accountNumber;
    private String granteeName;
    private String grantorName;
    private Authorization authorization;
}
