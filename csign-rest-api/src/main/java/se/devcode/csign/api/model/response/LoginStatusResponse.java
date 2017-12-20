package se.devcode.csign.api.model.response;

import io.swagger.annotations.ApiModel;
import se.devcode.csign.api.model.ScenarioCode;
import se.devcode.csign.api.model.User;

import java.time.ZonedDateTime;

@ApiModel
public class LoginStatusResponse {

    private User user;
    private String sessionId;
    private ScenarioCode scenario;
    private ZonedDateTime dateTime;

    public static final String UN_SUCCESSFUL_LOGIN= "Unsuccessful login";

    public LoginStatusResponse(User user,
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
