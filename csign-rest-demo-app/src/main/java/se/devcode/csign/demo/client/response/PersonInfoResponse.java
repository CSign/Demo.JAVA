package se.devcode.csign.demo.client.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.devcode.csign.demo.client.model.Person;

public class PersonInfoResponse {

    private Person person;

    @JsonCreator
    public PersonInfoResponse(@JsonProperty("person") Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
