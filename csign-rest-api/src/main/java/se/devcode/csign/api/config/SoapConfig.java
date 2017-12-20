package se.devcode.csign.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import se.devcode.csign.api.client.CSignClient;
import se.devcode.csign.api.util.CSignHelper;

@Configuration
public class SoapConfig {

    @Value("${csign.login.api.url}")
    private String csignLoginApiUrl;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("se.csign.integration.service.v1");
        return marshaller;
    }

    @Bean
    public CSignHelper cSignHelper() {
        return new CSignHelper();
    }

    @Bean
    public CSignClient loginClient(Jaxb2Marshaller marshaller, CSignHelper cSignHelper) {
        CSignClient client = new CSignClient(cSignHelper);
        client.setDefaultUri(csignLoginApiUrl);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
