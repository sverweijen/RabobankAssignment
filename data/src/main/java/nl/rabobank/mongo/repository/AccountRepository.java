package nl.rabobank.mongo.repository;

import nl.rabobank.mongo.model.AccountDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<AccountDoc, String> {

    Optional<AccountDoc> findByAccountNumber(String accountNumber);
}
