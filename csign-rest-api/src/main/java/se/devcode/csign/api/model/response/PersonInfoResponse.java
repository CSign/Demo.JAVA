package se.devcode.csign.api.model.response;

import io.swagger.annotations.ApiModel;
import se.devcode.csign.api.model.Person;


@ApiModel
public class PersonInfoResponse {

    private Person person;

    public PersonInfoResponse(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
