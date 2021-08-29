package nl.rabobank.mapper;

import nl.rabobank.account.AccountType;
import nl.rabobank.authorizations.Authorization;
import nl.rabobank.model.PowerOfAttorneyRequest;
import nl.rabobank.mongo.model.AccountDoc;
import nl.rabobank.mongo.model.PowerOfAttorneyDoc;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PowerOfAttorneyMapperTest {
    private final PowerOfAttorneyMapper mapper = Mappers.getMapper(PowerOfAttorneyMapper.class);

    @Test
    public void mapToPowerOfAttorney() {
        PowerOfAttorneyRequest powerOfAttorneyRequest = new PowerOfAttorneyRequest();
        powerOfAttorneyRequest.setAccountNumber("1111111111");
        powerOfAttorneyRequest.setGranteeName("Grantee 1");
        powerOfAttorneyRequest.setGrantorName("Holder 1");
        powerOfAttorneyRequest.setAuthorization(Authorization.READ);

        AccountDoc account = new AccountDoc();
        account.setId("1");
        account.setAccountNumber("1111111111");
        account.setAccountHolderName("Holder 1");
        account.setBalance(2500.00);
        account.setAccountType(AccountType.PAYMENT);

        PowerOfAttorneyDoc powerOfAttorneyDoc = mapper.mapToPowerOfAttorney(powerOfAttorneyRequest, account);

        assertNull(powerOfAttorneyDoc.getId());
        assertEquals("Grantee 1", powerOfAttorneyDoc.getGranteeName());
        assertEquals("Holder 1", powerOfAttorneyDoc.getGrantorName());
        assertEquals(Authorization.READ, powerOfAttorneyDoc.getAuthorization());
        assertEquals(account, powerOfAttorneyDoc.getAccount());

    }
}
