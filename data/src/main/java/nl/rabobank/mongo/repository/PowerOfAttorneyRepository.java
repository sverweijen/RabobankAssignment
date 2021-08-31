package nl.rabobank.mongo.repository;

import nl.rabobank.authorizations.Authorization;
import nl.rabobank.mongo.model.AccountDoc;
import nl.rabobank.mongo.model.PowerOfAttorneyDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PowerOfAttorneyRepository extends MongoRepository<PowerOfAttorneyDoc, String> {

  List<PowerOfAttorneyDoc> findByGranteeName(String granteeName);

  List<PowerOfAttorneyDoc> findByGranteeNameAndAuthorization(
      String granteeName, Authorization authorization);

  Optional<PowerOfAttorneyDoc> findByAccountAndGranteeNameAndAuthorization(
          AccountDoc account, String granteeName, Authorization authorization);
}
