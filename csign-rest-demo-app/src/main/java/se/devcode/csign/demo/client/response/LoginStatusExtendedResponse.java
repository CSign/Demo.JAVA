package se.devcode.csign.demo.client.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.devcode.csign.demo.client.model.Person;
import se.devcode.csign.demo.client.model.ScenarioCode;
import se.devcode.csign.demo.client.model.User;

import java.time.ZonedDateTime;

public class LoginStatusExtendedResponse {

    private User user;
    @JsonProperty("person")
    private Person person;
    private String sessionId;
    private ScenarioCode scenario;
    private ZonedDateTime dateTime;

    @JsonCreator
    public LoginStatusExtendedResponse(@JsonProperty("user") User user,
                                       @JsonProperty("sessionId") String sessionId,
                                       @JsonProperty("scenario") ScenarioCode scenario,
                                       @JsonProperty("dateTime") ZonedDateTime dateTime) {
        this.user = user;
        this.sessionId = sessionId;
        this.scenario = scenario;
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    @JsonProperty("person")
    public Person getPerson() {
        return person;
    }

    @JsonProperty("person")
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
