package se.devcode.csign.api.model.response;

import io.swagger.annotations.ApiModel;

@ApiModel
public class PrepareLoginResponse {

    private final String loginUrl;
    private final String sessionId;

    public PrepareLoginResponse(String loginUrl, String sessionId) {
        this.loginUrl = loginUrl;
        this.sessionId = sessionId;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public String getSessionId() {
        return sessionId;
    }
}
