package se.devcode.csign.api.model.request;

import se.devcode.csign.api.model.ScenarioCode;

public class LoginRequest {

    private ScenarioCode scenarioCode;
    private String sessionId;
    private String returnUrl;

    public ScenarioCode getScenarioCode() {
        return scenarioCode;
    }

    public void setScenarioCode(ScenarioCode scenarioCode) {
        this.scenarioCode = scenarioCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
