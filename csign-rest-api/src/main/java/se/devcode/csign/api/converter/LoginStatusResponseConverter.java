package se.devcode.csign.api.converter;

import org.springframework.stereotype.Component;
import se.csign.integration.data.v1.Individual;
import se.csign.integration.data.v1.ScenarioLoginCallbackGetResponse;
import se.devcode.csign.api.model.ScenarioCode;
import se.devcode.csign.api.model.User;
import se.devcode.csign.api.model.response.LoginStatusResponse;

import javax.inject.Inject;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class LoginStatusResponseConverter implements Converter<ScenarioLoginCallbackGetResponse, LoginStatusResponse>{

    private final UserConverter userConverter;

    @Inject
    public LoginStatusResponseConverter(UserConverter userConverter){
        this.userConverter = userConverter;
    }

    @Override
    public LoginStatusResponse convert(ScenarioLoginCallbackGetResponse from) {
        Individual individual = from.getCallback().getIdentifiedUser().getValue().getIndividual();
        User user = userConverter.convert(individual);
        String sessionId = from.getCallback().getSessionId();
        ScenarioCode scenarioCode = ScenarioCode.fromCode(from.getCallback().getScenario());
        ZonedDateTime timeStamp = from.getTimeStamp().toGregorianCalendar().toInstant().atZone(ZoneId.systemDefault());
        return new LoginStatusResponse(user, sessionId, scenarioCode, timeStamp);
    }
}
