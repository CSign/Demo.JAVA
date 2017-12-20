package se.devcode.csign.demo.client.request;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import se.devcode.csign.demo.client.model.ScenarioCode;

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

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
