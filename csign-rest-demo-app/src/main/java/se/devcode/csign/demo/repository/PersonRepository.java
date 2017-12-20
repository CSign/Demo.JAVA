package se.devcode.csign.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.devcode.csign.demo.client.model.Person;

public interface PersonRepository extends MongoRepository<Person, String> {

    Person findBySocialSecurityNumber(String socialSecurityNumber);
}
