package nl.rabobank.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.rabobank.authorizations.Authorization;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class PowerOfAttorneyDoc {
    @Id private String id;
    private String granteeName;
    private String grantorName;
    private AccountDoc account;
    private Authorization authorization;
}
