package nl.rabobank.mongo.repository;

import nl.rabobank.authorizations.Authorization;
import nl.rabobank.mongo.model.PowerOfAttorneyDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PowerOfAttorneyRepository extends MongoRepository<PowerOfAttorneyDoc, String> {

    List<PowerOfAttorneyDoc> findByGranteeName(String granteeName);
    List<PowerOfAttorneyDoc> findByGranteeNameAndAuthorization(String granteeName, Authorization authorization);
}
