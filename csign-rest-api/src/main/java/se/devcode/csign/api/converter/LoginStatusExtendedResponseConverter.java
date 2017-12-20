package se.devcode.csign.api.converter;

        import org.springframework.stereotype.Component;
        import se.csign.integration.data.v1.Individual;
        import se.csign.integration.data.v1.ScenarioLoginCallbackGetResponse;
        import se.devcode.csign.api.model.ScenarioCode;
        import se.devcode.csign.api.model.User;
        import se.devcode.csign.api.model.response.LoginStatusExtendedResponse;

        import javax.inject.Inject;
        import java.time.ZoneId;
        import java.time.ZonedDateTime;

@Component
public class LoginStatusExtendedResponseConverter implements
        Converter<ScenarioLoginCallbackGetResponse, LoginStatusExtendedResponse> {

    private final UserConverter userConverter;

    @Inject
    public LoginStatusExtendedResponseConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public LoginStatusExtendedResponse convert(ScenarioLoginCallbackGetResponse from) {
        Individual individual = from.getCallback().getIdentifiedUser().getValue().getIndividual();
        User user = userConverter.convert(individual);
        String sessionId = from.getCallback().getSessionId();
        ScenarioCode scenarioCode = ScenarioCode.fromCode(from.getCallback().getScenario());
        ZonedDateTime timeStamp = from.getTimeStamp().toGregorianCalendar().toInstant().atZone(ZoneId.systemDefault());
        return new LoginStatusExtendedResponse(user, sessionId, scenarioCode, timeStamp);
    }
}
