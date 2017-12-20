package se.devcode.csign.demo.service;

import org.springframework.stereotype.Service;
import se.devcode.csign.demo.client.model.Person;
import se.devcode.csign.demo.repository.PersonRepository;

import javax.inject.Inject;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Inject
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person findById(String id) {
        return personRepository.findOne(id);
    }

    public Person findBySsn(String ssn) {
        return personRepository.findBySocialSecurityNumber(ssn);
    }

    public String findIdBySsn(String ssn) {
        Person person = findBySsn(ssn);
        if(person !=null){
            return person.getId();
        }else{
            return null;
        }
    }

    public Person saveOrUpdate(Person product) {
        personRepository.save(product);
        return product;
    }

    public void delete(String id) {
        personRepository.delete(id);
    }
}
