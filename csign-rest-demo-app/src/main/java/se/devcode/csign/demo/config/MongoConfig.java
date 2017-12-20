package se.devcode.csign.demo.config;

import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

@Configuration
public class MongoConfig {

    private static final String MONGO_DB_URL = "localhost";
    private static final String MONGO_DB_NAME = "embeded_db";

    @Bean
    public MongoTemplate mongoTemplate() throws IOException {
        EmbeddedMongoFactoryBean embeddedMongoFactory = new EmbeddedMongoFactoryBean();
        embeddedMongoFactory.setBindIp(MONGO_DB_URL);
        MongoClient mongoClient = embeddedMongoFactory.getObject();
        return new MongoTemplate(mongoClient, MONGO_DB_NAME);
    }
}

