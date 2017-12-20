package se.devcode.csign.demo.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.devcode.csign.demo.client.error.CSignRestClientException;
import se.devcode.csign.demo.client.request.LoginRequest;
import se.devcode.csign.demo.client.response.LoginStatusExtendedResponse;
import se.devcode.csign.demo.client.response.LoginStatusResponse;
import se.devcode.csign.demo.client.response.PersonInfoResponse;
import se.devcode.csign.demo.client.response.PrepareLoginResponse;
import se.devcode.csign.demo.config.CSignRestClientConfig;

@FeignClient(name = "CSignRestClient", url = "${csign.rest.service.url}", configuration = CSignRestClientConfig.class, decode404 = true)
public interface CSignRestClient {

    @RequestMapping(value = "rest/v1/login", method = RequestMethod.POST)
    PrepareLoginResponse prepareLogin(@RequestBody LoginRequest request) throws CSignRestClientException;

    @RequestMapping(value = "rest/v1/login/status/{sessionId}", method = RequestMethod.GET)
    LoginStatusResponse getLoginResult(@PathVariable("sessionId") String sessionId) throws CSignRestClientException;

    @RequestMapping(value = "rest/v1/login/status/{sessionId}/extended", method = RequestMethod.GET)
    LoginStatusExtendedResponse getExtendedLoginResult(@PathVariable("sessionId") String sessionId) throws CSignRestClientException;

    @RequestMapping(value = "rest/v1/person/info/{ssn}", method = RequestMethod.GET)
    PersonInfoResponse getPersonInfo(@PathVariable("ssn") String ssn) throws CSignRestClientException;

}
