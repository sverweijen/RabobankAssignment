package nl.rabobank.controller;

import lombok.RequiredArgsConstructor;
import nl.rabobank.authorizations.Authorization;
import nl.rabobank.model.PowerOfAttorneyRequest;
import nl.rabobank.mongo.model.PowerOfAttorneyDoc;
import nl.rabobank.service.PowerOfAttorneyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RestController
@RequestMapping("/power-of-attorney")
public class PowerOfAttorneyController {

    private final PowerOfAttorneyService powerOfAttorneyService;

    @PostMapping
    public ResponseEntity<PowerOfAttorneyDoc> createPowerOfAttorney(@RequestBody PowerOfAttorneyRequest powerOfAttorneyRequest) {
        PowerOfAttorneyDoc powerOfAttorney = powerOfAttorneyService.createPowerOfAttorney(powerOfAttorneyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(powerOfAttorney);
    }

    @GetMapping
    public ResponseEntity<List<PowerOfAttorneyDoc>> getPowerOfAttorneysByFilter(
            @RequestParam String granteeName, @RequestParam Optional<Authorization> authorization) {
        List<PowerOfAttorneyDoc> powerOfAttorneyDoc = powerOfAttorneyService.getPowerOfAttorneysByFilter(granteeName, authorization);
        return ResponseEntity.status(HttpStatus.OK).body(powerOfAttorneyDoc);
    }
}
