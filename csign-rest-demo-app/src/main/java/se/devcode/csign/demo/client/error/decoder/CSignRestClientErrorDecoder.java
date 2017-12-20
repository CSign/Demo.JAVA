package se.devcode.csign.demo.client.error.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import se.devcode.csign.demo.client.error.CSignRestClientException;

import static feign.FeignException.errorStatus;

public class CSignRestClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() >= 400 && response.status() <= 599) {
            return new CSignRestClientException(
                    response.status(),
                    response.reason());
        }
        return errorStatus(methodKey, response);
    }
}
