package se.devcode.csign.demo.service;

import org.springframework.stereotype.Service;
import se.devcode.csign.demo.client.CSignRestClient;
import se.devcode.csign.demo.client.error.CSignRestClientException;
import se.devcode.csign.demo.client.request.LoginRequest;
import se.devcode.csign.demo.client.response.LoginStatusExtendedResponse;
import se.devcode.csign.demo.client.response.LoginStatusResponse;
import se.devcode.csign.demo.client.response.PersonInfoResponse;
import se.devcode.csign.demo.client.response.PrepareLoginResponse;

import javax.inject.Inject;

@Service
public class CSignRestService {

    private final CSignRestClient cSignRestClient;

    @Inject
    public CSignRestService(CSignRestClient cSignRestClient) {
        this.cSignRestClient = cSignRestClient;
    }

    public PrepareLoginResponse prepareLogin(LoginRequest request) throws CSignRestClientException {
        return cSignRestClient.prepareLogin(request);
    }

    public LoginStatusResponse getLoginResult(String sessionId) throws CSignRestClientException {
        return cSignRestClient.getLoginResult(sessionId);
    }

    public LoginStatusExtendedResponse getExtendedLoginResult(String sessionId) throws CSignRestClientException {
        return cSignRestClient.getExtendedLoginResult(sessionId);
    }

    public PersonInfoResponse getPersonInfo(String ssn) throws CSignRestClientException {
        return cSignRestClient.getPersonInfo(ssn);
    }

}
