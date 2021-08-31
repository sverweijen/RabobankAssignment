package nl.rabobank.service;

import nl.rabobank.authorizations.Authorization;
import nl.rabobank.mapper.PowerOfAttorneyMapper;
import nl.rabobank.model.PowerOfAttorneyRequest;
import nl.rabobank.mongo.model.AccountDoc;
import nl.rabobank.mongo.model.PowerOfAttorneyDoc;
import nl.rabobank.mongo.repository.AccountRepository;
import nl.rabobank.mongo.repository.PowerOfAttorneyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PowerOfAttorneyServiceTest {

  @InjectMocks private PowerOfAttorneyService powerOfAttorneyService;

  @Mock private AccountRepository accountRepository;
  @Mock private PowerOfAttorneyRepository powerOfAttorneyRepository;
  @Mock private PowerOfAttorneyMapper powerOfAttorneyMapper;
  @Mock private PowerOfAttorneyDoc powerOfAttorneyDoc;

  private AccountDoc accountDoc;
  private PowerOfAttorneyRequest powerOfAttorneyRequest;
  private List<PowerOfAttorneyDoc> powerOfAttorneyDocs;

  private final String grantee = "grantee_1";

  @BeforeEach
  public void setup() {
    String accountNumber = "fake_accountNumber";
    String accountHolder = "holder_1";

    accountDoc = new AccountDoc();
    accountDoc.setAccountNumber(accountNumber);
    accountDoc.setAccountHolderName(accountHolder);

    powerOfAttorneyRequest = new PowerOfAttorneyRequest();
    powerOfAttorneyRequest.setAccountNumber(accountNumber);
    powerOfAttorneyRequest.setGranteeName(grantee);
    powerOfAttorneyRequest.setGrantorName(accountHolder);
    powerOfAttorneyRequest.setAuthorization(Authorization.READ);

    PowerOfAttorneyDoc powerOfAttorneyDoc = new PowerOfAttorneyDoc();
    powerOfAttorneyDoc.setGranteeName(grantee);
    powerOfAttorneyDoc.setGrantorName(accountHolder);
    powerOfAttorneyDoc.setAuthorization(Authorization.READ);
    powerOfAttorneyDoc.setAccount(accountDoc);
    powerOfAttorneyDocs = new ArrayList<>();
    powerOfAttorneyDocs.add(powerOfAttorneyDoc);
  }

  @Test
  void createPowerOfAttorney() {
    when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(accountDoc));
    when(powerOfAttorneyRepository.findByAccountAndGranteeNameAndAuthorization(
            any(AccountDoc.class), anyString(), any(Authorization.class)))
            .thenReturn(Optional.empty());
    when(powerOfAttorneyMapper.mapToPowerOfAttorney(
            any(PowerOfAttorneyRequest.class), any(AccountDoc.class)))
        .thenReturn(powerOfAttorneyDoc);
    when(powerOfAttorneyRepository.save(any(PowerOfAttorneyDoc.class)))
        .thenReturn(powerOfAttorneyDoc);

    PowerOfAttorneyDoc result =
        powerOfAttorneyService.createPowerOfAttorney(powerOfAttorneyRequest);

    assertNotNull(result);
    verify(accountRepository).findByAccountNumber(anyString());
    verify(powerOfAttorneyRepository)
            .findByAccountAndGranteeNameAndAuthorization(
                    any(AccountDoc.class), anyString(), any(Authorization.class));
    verify(powerOfAttorneyMapper)
        .mapToPowerOfAttorney(any(PowerOfAttorneyRequest.class), any(AccountDoc.class));
    verify(powerOfAttorneyRepository).save(any(PowerOfAttorneyDoc.class));
  }

  @Test
  void createPowerOfAttorney_accountIdEmpty() {
    when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());
    ResponseStatusException exception =
        assertThrows(
            ResponseStatusException.class,
            () -> powerOfAttorneyService.createPowerOfAttorney(powerOfAttorneyRequest));

    String expectedMessage = "Account does not exist";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

    verify(accountRepository).findByAccountNumber(anyString());
  }

  @Test
  void createPowerOfAttorney_accountHolderDoesNotMatch() {
    accountDoc.setAccountHolderName("grantor_1");
    when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(accountDoc));

    ResponseStatusException exception =
        assertThrows(
            ResponseStatusException.class,
            () -> powerOfAttorneyService.createPowerOfAttorney(powerOfAttorneyRequest));

    String expectedMessage = "holder_1 is not the account holder";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

    verify(accountRepository).findByAccountNumber(anyString());
  }

  @Test
  void createPowerOfAttorney_PowerOfAttorneyAlreadyExists() {
    when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(accountDoc));
    when(powerOfAttorneyRepository.findByAccountAndGranteeNameAndAuthorization(
            any(AccountDoc.class), anyString(), any(Authorization.class)))
        .thenReturn(Optional.of(powerOfAttorneyDoc));

    ResponseStatusException exception =
        assertThrows(
            ResponseStatusException.class,
            () -> powerOfAttorneyService.createPowerOfAttorney(powerOfAttorneyRequest));

    String expectedMessage = "Power of Attorney already exists";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
    assertEquals(HttpStatus.CONFLICT, exception.getStatus());

    verify(accountRepository).findByAccountNumber(anyString());
    verify(powerOfAttorneyRepository)
        .findByAccountAndGranteeNameAndAuthorization(
            any(AccountDoc.class), anyString(), any(Authorization.class));
  }

  @Test
  void getPowerOfAttorneysByFilter() {
    when(powerOfAttorneyRepository.findByGranteeNameAndAuthorization(
            anyString(), any(Authorization.class)))
        .thenReturn(powerOfAttorneyDocs);

    List<PowerOfAttorneyDoc> result =
        powerOfAttorneyService.getPowerOfAttorneysByFilter(
            grantee, Optional.of(Authorization.READ));

    assertNotNull(result);
    verify(powerOfAttorneyRepository)
        .findByGranteeNameAndAuthorization(anyString(), any(Authorization.class));
  }

  @Test
  void getPowerOfAttorneysByFilter_authorizationIsEmpty() {
    when(powerOfAttorneyRepository.findByGranteeName(anyString())).thenReturn(powerOfAttorneyDocs);

    List<PowerOfAttorneyDoc> result =
        powerOfAttorneyService.getPowerOfAttorneysByFilter(grantee, Optional.empty());

    assertNotNull(result);
    verify(powerOfAttorneyRepository).findByGranteeName(anyString());
  }
}
