package se.devcode.csign.api.converter;

import org.springframework.stereotype.Component;
import se.csign.integration.data.v1.Individual;
import se.devcode.csign.api.model.User;

@Component
public class UserConverter implements Converter<Individual, User> {

    @Override
    public User convert(Individual from) {
        User user = new User(
                from.getSSN().getValue(),
                from.getFirstName().getValue(),
                from.getLastName().getValue(),
                from.getCulture().getValue());
        return user;
    }
}
