package nl.rabobank.service;

import lombok.RequiredArgsConstructor;
import nl.rabobank.authorizations.Authorization;
import nl.rabobank.mapper.PowerOfAttorneyMapper;
import nl.rabobank.model.PowerOfAttorneyRequest;
import nl.rabobank.mongo.model.AccountDoc;
import nl.rabobank.mongo.model.PowerOfAttorneyDoc;
import nl.rabobank.mongo.repository.AccountRepository;
import nl.rabobank.mongo.repository.PowerOfAttorneyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PowerOfAttorneyService {

    private final AccountRepository accountRepository;
    private final PowerOfAttorneyRepository powerOfAttorneyRepository;
    private final PowerOfAttorneyMapper mapper;

    public PowerOfAttorneyDoc createPowerOfAttorney(@RequestBody PowerOfAttorneyRequest powerOfAttorneyRequest) {
        Optional<AccountDoc> account = accountRepository.findByAccountNumber(powerOfAttorneyRequest.getAccountNumber());

        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account does not exist");
        }
        if (!account.get().getAccountHolderName().equals(powerOfAttorneyRequest.getGrantorName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, powerOfAttorneyRequest.getGrantorName() + " is not the account holder");
        }

        PowerOfAttorneyDoc powerOfAttorneyDoc = mapper.mapToPowerOfAttorney(powerOfAttorneyRequest, account.get());
        return powerOfAttorneyRepository.save(powerOfAttorneyDoc);
    }

    public List<PowerOfAttorneyDoc> getPowerOfAttorneysByFilter(
            String granteeName, Optional<Authorization> authorization) {
        List<PowerOfAttorneyDoc> powerOfAttorneyDoc = new ArrayList<>();
        if (authorization.isEmpty()) {
            powerOfAttorneyDoc = powerOfAttorneyRepository.findByGranteeName(granteeName);
        }
        if (authorization.isPresent()) {
            powerOfAttorneyDoc = powerOfAttorneyRepository.findByGranteeNameAndAuthorization(granteeName, authorization.get());
        }
        return powerOfAttorneyDoc;
    }
}
