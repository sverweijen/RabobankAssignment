package nl.rabobank.controller;

import nl.rabobank.mongo.model.AccountDoc;
import nl.rabobank.mongo.repository.AccountRepository;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

  @InjectMocks private AccountController accountController;
  @Mock private AccountRepository accountRepository;

  private AccountDoc accountDoc;
  private List<AccountDoc> accountDocs;

  private final String accountNumber = "fake_accountNumber";

  @BeforeEach
  public void setup() {
    String accountHolder = "holder_1";
    accountDoc = new AccountDoc();
    accountDoc.setAccountNumber(accountNumber);
    accountDoc.setAccountHolderName(accountHolder);
    accountDocs = new ArrayList<>();
    accountDocs.add(accountDoc);
  }

  @Test
  void getAccountByAccountNumber() {
    when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(accountDoc));

    ResponseEntity<AccountDoc> result = accountController.getAccountByAccountNumber(accountNumber);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(accountDoc, result.getBody());
    verify(accountRepository).findByAccountNumber(anyString());
  }

  @Test
  void getAccountByAccountNumber_notFound() {
    when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());

    ResponseEntity<AccountDoc> result = accountController.getAccountByAccountNumber(accountNumber);

    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    assertNull(result.getBody());
    verify(accountRepository).findByAccountNumber(anyString());
  }

  @Test
  void getAllAccounts() {
    when(accountRepository.findAll()).thenReturn(accountDocs);

    ResponseEntity<List<AccountDoc>> result = accountController.getAllAccounts();

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(accountDocs, result.getBody());
    verify(accountRepository).findAll();
  }
}
