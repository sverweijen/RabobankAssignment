package nl.rabobank.controller;

import nl.rabobank.authorizations.Authorization;
import nl.rabobank.model.PowerOfAttorneyRequest;
import nl.rabobank.mongo.model.AccountDoc;
import nl.rabobank.mongo.model.PowerOfAttorneyDoc;
import nl.rabobank.service.PowerOfAttorneyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PowerOfAttorneyControllerTest {

  @InjectMocks private PowerOfAttorneyController powerOfAttorneyController;
  @Mock private PowerOfAttorneyService powerOfAttorneyService;

  private PowerOfAttorneyRequest powerOfAttorneyRequest;
  private List<PowerOfAttorneyDoc> powerOfAttorneyDocs;
  private PowerOfAttorneyDoc powerOfAttorneyDoc;

  private final String grantee = "grantee_1";

  @BeforeEach
  public void setup() {
    String accountNumber = "fake_accountNumber";
    String accountHolder = "holder_1";

    AccountDoc accountDoc = new AccountDoc();
    accountDoc.setAccountNumber(accountNumber);
    accountDoc.setAccountHolderName(accountHolder);

    powerOfAttorneyRequest = new PowerOfAttorneyRequest();
    powerOfAttorneyRequest.setAccountNumber(accountNumber);
    powerOfAttorneyRequest.setGranteeName(grantee);
    powerOfAttorneyRequest.setGrantorName(accountHolder);
    powerOfAttorneyRequest.setAuthorization(Authorization.READ);

    powerOfAttorneyDoc = new PowerOfAttorneyDoc();
    powerOfAttorneyDoc.setGranteeName(grantee);
    powerOfAttorneyDoc.setGrantorName(accountHolder);
    powerOfAttorneyDoc.setAuthorization(Authorization.READ);
    powerOfAttorneyDoc.setAccount(accountDoc);
    powerOfAttorneyDocs = new ArrayList<>();
    powerOfAttorneyDocs.add(powerOfAttorneyDoc);
  }

  @Test
  void createPowerOfAttorney() {
    when(powerOfAttorneyService.createPowerOfAttorney(any(PowerOfAttorneyRequest.class)))
        .thenReturn(powerOfAttorneyDoc);

    ResponseEntity<PowerOfAttorneyDoc> result =
        powerOfAttorneyController.createPowerOfAttorney(powerOfAttorneyRequest);

    assertEquals(HttpStatus.CREATED, result.getStatusCode());
    assertEquals(powerOfAttorneyDoc, result.getBody());
    verify(powerOfAttorneyService).createPowerOfAttorney(any(PowerOfAttorneyRequest.class));
  }

  @Test
  void getPowerOfAttorneysByFilter() {
    when(powerOfAttorneyService.getPowerOfAttorneysByFilter(anyString(), any(Optional.class)))
        .thenReturn(powerOfAttorneyDocs);

    ResponseEntity<List<PowerOfAttorneyDoc>> result =
        powerOfAttorneyController.getPowerOfAttorneysByFilter(
            grantee, Optional.of(Authorization.READ));

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(powerOfAttorneyDocs, result.getBody());
    verify(powerOfAttorneyService).getPowerOfAttorneysByFilter(anyString(), any(Optional.class));
  }
}
