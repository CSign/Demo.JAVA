package se.devcode.csign.demo.client.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PrepareLoginResponse {

    private final String loginUrl;
    private final String sessionId;

    @JsonCreator
    public PrepareLoginResponse(@JsonProperty("loginUrl") String loginUrl,
                                @JsonProperty("sessionId") String sessionId) {
        this.loginUrl = loginUrl;
        this.sessionId = sessionId;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public String getSessionId() {
        return sessionId;
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
