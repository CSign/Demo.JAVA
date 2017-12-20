package se.devcode.csign.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("se.devcode.csign.api.resources"))
                .paths(PathSelectors.regex("/rest.*"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("CSign support", "http://www.csign.se", "info@csign.se");
        return new ApiInfoBuilder()
                .title("CSign REST Api")
                .version("1.0")
                .contact(contact)
                .description("This REST api includes Authentication with national electronic credentials such as BankId " +
                        "or other digital and national ID documents. As well as retrieving extended person information " +
                        "as well as digital signing of documents and payments etc.")
                .build();
    }
}
