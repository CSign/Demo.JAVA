package se.devcode.csign.api.model.response;

import io.swagger.annotations.ApiModel;
import se.devcode.csign.api.model.Person;
import se.devcode.csign.api.model.ScenarioCode;
import se.devcode.csign.api.model.User;

import java.time.ZonedDateTime;

@ApiModel
public class LoginStatusExtendedResponse {

    private User user;
    private Person person;
    private String sessionId;
    private ScenarioCode scenario;
    private ZonedDateTime dateTime;

    public static final String UN_SUCCESSFUL_LOGIN= "Unsuccessful login";

    public LoginStatusExtendedResponse(User user,
                                       String sessionId,
                                       ScenarioCode scenario,
                                       ZonedDateTime dateTime) {
        this.user = user;
        this.sessionId = sessionId;
        this.scenario = scenario;
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getSessionId() {
        return sessionId;
    }

    public ScenarioCode getScenario() {
        return scenario;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }


}
