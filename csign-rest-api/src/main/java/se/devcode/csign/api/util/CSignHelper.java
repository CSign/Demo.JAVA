package se.devcode.csign.api.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import se.csign.integration.data.v1.AuthorizationData;

@Configuration
public class CSignHelper {

    @Value("${csign.integration.key}")
    private String csignIntegrationKey;

    @Value("${csign.integration.id}")
    private int csignIntegrationId;

    public AuthorizationData getAuthorization() {
        AuthorizationData authorizationData = new AuthorizationData();
        authorizationData.setIntegrationId(csignIntegrationId);
        authorizationData.setIntegrationKey(csignIntegrationKey);
        return authorizationData;
    }
}
