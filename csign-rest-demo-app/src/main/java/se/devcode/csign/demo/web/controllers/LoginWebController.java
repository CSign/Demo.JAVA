package se.devcode.csign.demo.web.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import se.devcode.csign.demo.client.error.CSignRestClientException;
import se.devcode.csign.demo.client.model.Person;
import se.devcode.csign.demo.client.model.ScenarioCode;
import se.devcode.csign.demo.client.request.LoginRequest;
import se.devcode.csign.demo.client.response.LoginStatusExtendedResponse;
import se.devcode.csign.demo.client.response.LoginStatusResponse;
import se.devcode.csign.demo.client.response.PrepareLoginResponse;
import se.devcode.csign.demo.service.CSignRestService;
import se.devcode.csign.demo.service.PersonService;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class LoginWebController {

    private final CSignRestService cSignRestService;
    private final PersonService personService;

    @Value("${callback.return.url}")
    private String callbackReturnUrl;

    @Value("${callback.extended.url}")
    private String callbackExtendedUrl;

    @Inject
    public LoginWebController(CSignRestService cSignRestService,
                              PersonService personService) {
        this.cSignRestService = cSignRestService;
        this.personService = personService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/login")
    public ModelAndView login() throws CSignRestClientException {
        final String sessionId = UUID.randomUUID().toString();
        LoginRequest loginRequest = withLoginRequest(
                sessionId,
                ScenarioCode.ANY_INDIVIDUAL,
                withReturnUrl(sessionId));
        PrepareLoginResponse prepareLoginResponse = cSignRestService.prepareLogin(loginRequest);
        return new ModelAndView("login", "loginUrl", prepareLoginResponse.getLoginUrl());
    }

    @GetMapping("/login/result/{sessionId}")
    public String result(@PathVariable("sessionId") String sessionId,
                         HttpSession session) throws CSignRestClientException {
        LoginStatusResponse response = cSignRestService.getLoginResult(sessionId);
        String ssn = "00" + response.getUser().getSocialSecurityNumber().substring(2);
        String id = personService.findIdBySsn(ssn);
        boolean personAlreadyExists = id != null;
        final String name = response.getUser().getFirstName() + " , " + response.getUser().getLastName();
        session.setAttribute("user", name);
        if (personAlreadyExists) {
            session.setAttribute("id", id);
        }
        return "result";
    }

    @GetMapping("/login/result/extended/{sessionId}")
    public String extended(@PathVariable("sessionId") String sessionId,
                           HttpSession session) throws CSignRestClientException {
        LoginStatusExtendedResponse response = cSignRestService.getExtendedLoginResult(sessionId);

        String ssn = "00" + response.getUser().getSocialSecurityNumber().substring(2);
        String id = personService.findIdBySsn(ssn);
        boolean personAlreadyExists = id != null;
        Person storedPerson;
        if (personAlreadyExists) {
            storedPerson = personService.findById(id);
        } else {
            storedPerson = personService.saveOrUpdate(response.getPerson());
        }
        final String name = response.getUser().getFirstName() + " , " + response.getUser().getLastName();
        session.setAttribute("user", name);
        session.setAttribute("id", storedPerson.getId());
        return "extended";
    }

    @GetMapping("/view/{id}")
    public ModelAndView view(@PathVariable("id") String id) throws IOException {
        Person person = personService.findById(id);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(person);
        Map<String, String> map = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {
        });
        removeSensitiveItems(map);
        return new ModelAndView("person", "map", map);
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        session.removeAttribute("id");
        session.invalidate();
        return "index";
    }

    @GetMapping("/signUp")
    public ModelAndView signUp() throws CSignRestClientException {
        final String sessionId = UUID.randomUUID().toString();
        LoginRequest loginRequest = withLoginRequest(
                sessionId,
                ScenarioCode.ANY_INDIVIDUAL,
                withExtendedUrl(sessionId));
        PrepareLoginResponse prepareLoginResponse = cSignRestService.prepareLogin(loginRequest);
        return new ModelAndView("signup", "loginUrl", prepareLoginResponse.getLoginUrl());
    }

    private LoginRequest withLoginRequest(String sessionId,
                                          ScenarioCode scenarioCode,
                                          String returnUrl) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSessionId(sessionId);
        loginRequest.setScenarioCode(scenarioCode);
        loginRequest.setReturnUrl(returnUrl);
        return loginRequest;
    }

    private String withReturnUrl(String sessionId) {
        return String.format(callbackReturnUrl, sessionId);
    }

    private String withExtendedUrl(String sessionId) {
        return String.format(callbackExtendedUrl, sessionId);
    }

    private void removeSensitiveItems(Map<String, String> map) {
        if (map != null && !map.isEmpty()) {
            map.remove("socialSecurityNumber");
            map.remove("dob");
            map.remove("id");
        }
    }
}
