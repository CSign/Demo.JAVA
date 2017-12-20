package se.devcode.csign.api.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.csign.integration.data.v1.Consumer;
import se.csign.integration.data.v1.ScenarioLoginCallbackGetResponse;
import se.csign.integration.data.v1.ScenarioLoginResponse;
import se.devcode.csign.api.converter.LoginStatusExtendedResponseConverter;
import se.devcode.csign.api.converter.LoginStatusResponseConverter;
import se.devcode.csign.api.converter.PersonConverter;
import se.devcode.csign.api.converter.PersonInfoResponseConverter;
import se.devcode.csign.api.model.Person;
import se.devcode.csign.api.model.request.LoginRequest;
import se.devcode.csign.api.model.response.LoginStatusExtendedResponse;
import se.devcode.csign.api.model.response.LoginStatusResponse;
import se.devcode.csign.api.model.response.PersonInfoResponse;
import se.devcode.csign.api.model.response.PrepareLoginResponse;
import se.devcode.csign.api.service.CSignService;

import javax.inject.Inject;

@CrossOrigin
@RestController
@RequestMapping("/rest/v1")
@Api(tags = {"/rest/v1"}, description = "CSign REST Resource")
public class CSignRestController {

    private final CSignService cSignService;
    private final PersonConverter personConverter;
    private final LoginStatusResponseConverter loginStatusResponseConverter;
    private final LoginStatusExtendedResponseConverter loginStatusExtendedResponseConverter;
    private final PersonInfoResponseConverter personInfoResponseConverter;

    @Inject
    public CSignRestController(CSignService cSignService,
                               PersonConverter personConverter,
                               LoginStatusResponseConverter loginStatusResponseConverter,
                               LoginStatusExtendedResponseConverter loginStatusExtendedResponseConverter,
                               PersonInfoResponseConverter personInfoResponseConverter) {
        this.cSignService = cSignService;
        this.personConverter = personConverter;
        this.loginStatusResponseConverter = loginStatusResponseConverter;
        this.loginStatusExtendedResponseConverter = loginStatusExtendedResponseConverter;
        this.personInfoResponseConverter = personInfoResponseConverter;
    }

    @ApiOperation(value = "Get login result", response = LoginStatusResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 401, message = "User not authenticated")})
    @RequestMapping(value = "/login/status/{sessionId}", method = RequestMethod.GET)
    public ResponseEntity getLoginResult(@ApiParam("Session Id") @PathVariable("sessionId") String sessionId) {
        ScenarioLoginCallbackGetResponse response = cSignService.getLoginResult(sessionId)
                .getScenarioLoginCallbackGetResult().getValue();

        boolean successfulLogin = response.getCallback() != null && response.getCallback().getIdentifiedUser() != null;

        if (successfulLogin) {
            return ResponseEntity.ok(loginStatusResponseConverter.convert(response));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoginStatusResponse.UN_SUCCESSFUL_LOGIN);
        }
    }

    @ApiOperation(value = "Prepare login", response = PrepareLoginResponse.class)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity prepareLogin(@ApiParam("Login request") @RequestBody LoginRequest request) {

        ScenarioLoginResponse scenarioLoginResponse = cSignService.prepareLogin(
                request.getScenarioCode().getCode(),
                request.getSessionId(),
                request.getReturnUrl()).getScenarioLoginResult().getValue();

        return ResponseEntity.ok(new PrepareLoginResponse(
                scenarioLoginResponse.getLoginUrl(),
                scenarioLoginResponse.getSessionId()));
    }

    @ApiOperation(value = "Get extended login result", response = LoginStatusExtendedResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "User not authenticated"),
            @ApiResponse(code = 404, message = "Person not found")})
    @RequestMapping(value = "/login/status/{sessionId}/extended", method = RequestMethod.GET)
    public ResponseEntity getExtendedLoginResult(@ApiParam("Session Id") @PathVariable("sessionId") String sessionId) {
        ScenarioLoginCallbackGetResponse response = cSignService.getLoginResult(sessionId)
                .getScenarioLoginCallbackGetResult().getValue();

        boolean successfulLogin = response.getCallback() != null && response.getCallback().getIdentifiedUser() != null;
        if (successfulLogin) {
            LoginStatusExtendedResponse loginStatusExtendedResponse = loginStatusExtendedResponseConverter
                    .convert(response);

            Consumer consumer = cSignService
                    .getPersonInfo(loginStatusExtendedResponse.getUser().getSocialSecurityNumber().substring(2))
                    .getConsumerDataGetResult().getValue()
                    .getConsumer().getValue();

            boolean personFound = consumer != null;
            if (personFound) {
                loginStatusExtendedResponse.setPerson(personConverter.convert(consumer));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Person.PERSON_NOT_FOUND);
            }
            return ResponseEntity.ok(loginStatusExtendedResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoginStatusExtendedResponse.UN_SUCCESSFUL_LOGIN);
        }
    }

    @ApiOperation(value = "Get person info by ssn", response = PersonInfoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Person not found")})
    @RequestMapping(value = "/person/info/{ssn}", method = RequestMethod.GET)
    public ResponseEntity getPersonInfo(@ApiParam(value = "Social Security Number", required = true)
                                        @PathVariable("ssn") String ssn) {
        Consumer response = cSignService.getPersonInfo(ssn)
                .getConsumerDataGetResult().getValue()
                .getConsumer().getValue();
        boolean personFound = response != null;
        if (personFound) {
            return ResponseEntity.ok(personInfoResponseConverter.convert(response));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Person.PERSON_NOT_FOUND);
        }
    }
}
