package nl.rabobank.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.rabobank.account.AccountType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class AccountDoc {
    @Id private String id;
    private String accountNumber;
    private String accountHolderName;
    private Double balance;
    private AccountType accountType;
}
