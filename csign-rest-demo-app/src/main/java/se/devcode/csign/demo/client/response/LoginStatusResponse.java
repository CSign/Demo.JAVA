package se.devcode.csign.demo.client.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.devcode.csign.demo.client.model.ScenarioCode;
import se.devcode.csign.demo.client.model.User;

import java.time.ZonedDateTime;

public class LoginStatusResponse {

    private User user;
    private String sessionId;
    private ScenarioCode scenario;
    private ZonedDateTime dateTime;

    @JsonCreator
    public LoginStatusResponse(@JsonProperty("user") User user,
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
