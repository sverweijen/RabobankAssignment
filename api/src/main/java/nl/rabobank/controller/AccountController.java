package nl.rabobank.controller;

import lombok.RequiredArgsConstructor;
import nl.rabobank.mongo.model.AccountDoc;
import nl.rabobank.mongo.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountRepository accountRepository;

    @GetMapping(value = "/{account-number}")
    public ResponseEntity<AccountDoc> getAccountByAccountNumber(@PathVariable("account-number") String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(account -> ResponseEntity.status(HttpStatus.OK).body(account))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<AccountDoc>> getAllAccounts() {
        List<AccountDoc> accounts = accountRepository.findAll();
        return ResponseEntity.ok().body(accounts);
    }
}
