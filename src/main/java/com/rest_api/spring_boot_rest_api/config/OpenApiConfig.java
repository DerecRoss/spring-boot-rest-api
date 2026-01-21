package com.rest_api.spring_boot_rest_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // run this class in spring context runtime.
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("RESTFULL API with SPRING BOOT, DOCKER and KUBERNETES")
                        .version("V1")
                        .description("RESTFULL API with SPRING BOOT, DOCKER and KUBERNETES")
                        .termsOfService("https://github.com/DerecRoss")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://github.com/DerecRoss")));
    }
}
