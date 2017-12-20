package se.devcode.csign.api.converter;

import org.springframework.stereotype.Component;
import se.csign.integration.data.v1.Consumer;
import se.devcode.csign.api.model.Person;
import se.devcode.csign.api.model.response.PersonInfoResponse;

import javax.inject.Inject;

@Component
public class PersonInfoResponseConverter implements Converter<Consumer, PersonInfoResponse> {

    private final PersonConverter personConverter;

    @Inject
    public PersonInfoResponseConverter(PersonConverter personConverter) {
        this.personConverter = personConverter;
    }

    @Override
    public PersonInfoResponse convert(Consumer from) {
        Person person = personConverter.convert(from);
        return new PersonInfoResponse(person);
    }
}
