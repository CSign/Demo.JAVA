package se.devcode.csign.demo.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.devcode.csign.demo.client.error.decoder.CSignRestClientErrorDecoder;

@Configuration
public class CSignRestClientConfig {

    @Bean
    CSignRestClientErrorDecoder cSignRestClientErrorDecoder(){
        return new CSignRestClientErrorDecoder();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
