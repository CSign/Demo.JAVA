package se.devcode.csign.demo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import se.devcode.csign.demo.client.CSignRestClient;
import se.devcode.csign.demo.client.error.CSignRestClientException;
import se.devcode.csign.demo.client.model.Person;
import se.devcode.csign.demo.client.model.ScenarioCode;
import se.devcode.csign.demo.client.model.User;
import se.devcode.csign.demo.client.request.LoginRequest;
import se.devcode.csign.demo.client.response.LoginStatusExtendedResponse;
import se.devcode.csign.demo.client.response.LoginStatusResponse;
import se.devcode.csign.demo.client.response.PersonInfoResponse;
import se.devcode.csign.demo.client.response.PrepareLoginResponse;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class CSignRestServiceTest {

    private static final String RETURN_URL = "return.url.se";
    private static final String LOGIN_URL = "login.url.se";
    private static final String SESSION_ID = UUID.randomUUID().toString();
    private static final String FIRST_NAME = "Kalle";
    private static final String LAST_NAME = "Kula";
    private static final String SSN = "2109230579";
    private static final ZonedDateTime TIME_STAMP = ZonedDateTime.now(ZoneId.systemDefault());

    private static final String POST_CODE = "11359";
    private static final String TAXATION_YEAR = "2017";
    private static final String BOARD_MEMBER = "N";
    private static final String REAL_ESTATE_AVAILABLE_SEPARATELY = "N";
    private static final String TOWN = "Stockholm";
    private static final String SOLE_PROPRIETOR = "N";
    private static final String POSTAL_ADDRESS_LINE_1 = "Sveavägen 49 Lgh 1401";
    private static final String CULTURE = "SE";
    private static final String NAME_CODE = "20";
    private static final String CONSUMER_NAME = "Kula, Kalle";
    private static final String BIRTH_DAY = "230921";

    @MockBean
    private CSignRestClient cSignRestClient;

    private CSignRestService target;

    @Before
    public void setup() {
        target = new CSignRestService(cSignRestClient);
    }

    @Test
    public void prepareLogin_ShouldReturn() throws CSignRestClientException {
        final PrepareLoginResponse expected = withPrepareLoginResponse();
        final LoginRequest loginRequest = withLoginRequest();
        given(cSignRestClient.prepareLogin(loginRequest)).willReturn(expected);
        PrepareLoginResponse response = target.prepareLogin(withLoginRequest());
        assertPrepareLoginResponse(response);
    }

    @Test
    public void getLoginResult_ShouldReturn() throws CSignRestClientException {
        final LoginStatusResponse expected = withLoginStatusResponse();
        given(cSignRestClient.getLoginResult(SESSION_ID)).willReturn(expected);
        LoginStatusResponse response = target.getLoginResult(SESSION_ID);
        assertLoginStatusResponse(response);
    }

    @Test
    public void getExtendedLoginResult_ShouldReturn() throws CSignRestClientException {
        final LoginStatusExtendedResponse expected = withLoginStatusExtended();
        given(cSignRestClient.getExtendedLoginResult(SESSION_ID)).willReturn(expected);
        LoginStatusExtendedResponse response = target.getExtendedLoginResult(SESSION_ID);
        assertLoginStatusExtendedResponse(response);
    }

    @Test
    public void getPersonInfo_ShouldReturn() throws CSignRestClientException {
        final PersonInfoResponse expected = withPersonInfoResponse();
        given(cSignRestClient.getPersonInfo(SSN)).willReturn(expected);
        PersonInfoResponse response = target.getPersonInfo(SSN);
        assertThat(response, notNullValue());
        Person person = response.getPerson();
        assertPerson(person);
    }

    private void assertLoginStatusExtendedResponse(LoginStatusExtendedResponse response) {
        assertThat(response, notNullValue());
        assertThat(response.getSessionId(), is(SESSION_ID));
        assertThat(response.getDateTime(), is(TIME_STAMP));
        assertThat(response.getScenario(), equalTo(ScenarioCode.ANY_INDIVIDUAL));
        assertPerson(response.getPerson());
        assertUser(response.getUser());
    }

    private void assertPerson(Person person) {
        assertThat(person, notNullValue());
        assertThat(person.getBoardMember(), is(BOARD_MEMBER));
        assertThat(person.getConsumerName(), is(CONSUMER_NAME));
        assertThat(person.getCulture(), is(CULTURE));
        assertThat(person.getDob(), is(BIRTH_DAY));
        assertThat(person.getFirstName(), is(FIRST_NAME));
        assertThat(person.getNameCode(), is(NAME_CODE));
        assertThat(person.getNationalRegistrationAddressPostalAddressLine1(), is(POSTAL_ADDRESS_LINE_1));
        assertThat(person.getPostalAddressLine1(), is(POSTAL_ADDRESS_LINE_1));
        assertThat(person.getNationalRegistrationAddressTown(), is(TOWN));
        assertThat(person.getPostCode(), is(POST_CODE));
        assertThat(person.getRealEstateDataAvailableSeparately(), is(REAL_ESTATE_AVAILABLE_SEPARATELY));
        assertThat(person.getNationalRegistrationAddressPostCode(), is(POST_CODE));
        assertThat(person.getSoleProprietor(), is(SOLE_PROPRIETOR));
        assertThat(person.getTown(), is(TOWN));
        assertThat(person.getTaxationYearCCYY(), is(TAXATION_YEAR));
    }

    private void assertLoginStatusResponse(LoginStatusResponse response) {
        assertThat(response.getDateTime(), is(TIME_STAMP));
        assertThat(response.getScenario(), is(ScenarioCode.ANY_INDIVIDUAL));
        assertThat(response.getSessionId(), is(SESSION_ID));
        User user = response.getUser();
        assertUser(user);
    }

    private void assertUser(User user) {
        assertThat(user, notNullValue());
        assertThat(user.getFirstName(), is(FIRST_NAME));
        assertThat(user.getLastName(), is(LAST_NAME));
        assertThat(user.getSocialSecurityNumber(), is(SSN));
        assertThat(user.getLanguageTag(), is(Locale.getDefault().toLanguageTag()));
    }

    private void assertPrepareLoginResponse(PrepareLoginResponse response) {
        assertThat(response, notNullValue());
        assertThat(response.getLoginUrl(), is(LOGIN_URL));
        assertThat(response.getSessionId(), is(SESSION_ID));
    }

    private LoginStatusExtendedResponse withLoginStatusExtended() {
        LoginStatusExtendedResponse loginStatusExtendedResponse = new LoginStatusExtendedResponse(
                withUser(),
                SESSION_ID,
                ScenarioCode.ANY_INDIVIDUAL,
                TIME_STAMP
        );
        loginStatusExtendedResponse.setPerson(withPerson());
        return loginStatusExtendedResponse;
    }

    private PersonInfoResponse withPersonInfoResponse() {
        return new PersonInfoResponse(withPerson());
    }

    private Person withPerson() {
        Person person = new Person();
        person.setDob(BIRTH_DAY);
        person.setBoardMember(BOARD_MEMBER);
        person.setConsumerName(CONSUMER_NAME);
        person.setCulture(CULTURE);
        person.setNameCode(NAME_CODE);
        person.setTaxationYearCCYY(TAXATION_YEAR);
        person.setPostCode(POST_CODE);
        person.setNationalRegistrationAddressPostCode(POST_CODE);
        person.setRealEstateDataAvailableSeparately(REAL_ESTATE_AVAILABLE_SEPARATELY);
        person.setTown(TOWN);
        person.setNationalRegistrationAddressTown(TOWN);
        person.setSoleProprietor(SOLE_PROPRIETOR);
        person.setPostalAddressLine1(POSTAL_ADDRESS_LINE_1);
        person.setNationalRegistrationAddressPostalAddressLine1(POSTAL_ADDRESS_LINE_1);
        person.setSocialSecurityNumber(SSN);
        person.setFirstName(FIRST_NAME);
        person.setSurname(LAST_NAME);
        return person;
    }

    private LoginRequest withLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setReturnUrl(RETURN_URL);
        loginRequest.setScenarioCode(ScenarioCode.ANY_INDIVIDUAL);
        loginRequest.setSessionId(SESSION_ID);
        return loginRequest;
    }

    private LoginStatusResponse withLoginStatusResponse() {
        final User user = withUser();
        return new LoginStatusResponse(user, SESSION_ID, ScenarioCode.ANY_INDIVIDUAL, TIME_STAMP);
    }

    private User withUser() {
        return new User(SSN, FIRST_NAME, LAST_NAME, Locale.getDefault().toLanguageTag());
    }

    private PrepareLoginResponse withPrepareLoginResponse() {
        return new PrepareLoginResponse(LOGIN_URL, SESSION_ID);
    }
}
