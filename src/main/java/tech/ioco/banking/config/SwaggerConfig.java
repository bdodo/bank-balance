package tech.ioco.banking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
public Docket swaggerConfiguration() {
    String version = "1.0.0";

    return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiDetails(version))
            .enable(true)
            .select()
            .paths(PathSelectors.any())
            .apis(RequestHandlerSelectors.any())
            .build()
            .useDefaultResponseMessages(false)
            .forCodeGeneration(true);
}

    @Bean
    public UiConfiguration uiConfiguration(){
        return UiConfigurationBuilder.builder()
                .docExpansion(DocExpansion.LIST)
                .build();
    }

    private ApiInfo apiDetails(String version) {
        return new ApiInfo(
                "Bank Balance API",
                "API for managing bank account balances and withdrawals",
                version,
                "For Use by ATM consumers",
                new springfox.documentation.service.Contact("Bright Dodo", "http://www.github.com/bdodo", "brightdodo@gmail.com"),
                "API License",
                "http://www.github.com",
                Collections.emptyList()
        );
    }
}
