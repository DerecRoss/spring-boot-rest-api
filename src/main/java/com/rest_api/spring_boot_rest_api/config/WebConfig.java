package com.rest_api.spring_boot_rest_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // this class have configurations and possible bean declaration.
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.originPatterns}") // value in yml
    private String corsOriginPatterns = "";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowedCorsOrigin = corsOriginPatterns.split(",");
        registry.addMapping("/**")
                .allowedOrigins(allowedCorsOrigin)
//                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedMethods("*")
                .allowCredentials(true);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        // extension .xml and .json is deprecated on Spring Boot 2.6

        // with Query Param is the correctly - http://localhost:8080/api/person/v1/2?mediaType=xml

        //        configurer.favorParameter(true)
        //                .parameterName("mediaType")
        //                .ignoreAcceptHeader(true) // ignore override parameters in headers of request
        //                .useRegisteredExtensionsOnly(false)
        //                .defaultContentType(MediaType.APPLICATION_JSON)
        //                    .mediaType("json", MediaType.APPLICATION_JSON)
        //                    .mediaType("xml", MediaType.APPLICATION_XML);

//        configurer.favorParameter(false)
//                .ignoreAcceptHeader(false) // ignore override parameters in headers of request
//                .useRegisteredExtensionsOnly(false)
//                .defaultContentType(MediaType.APPLICATION_JSON)
//                    .mediaType("json", MediaType.APPLICATION_JSON)
//                    .mediaType("xml", MediaType.APPLICATION_XML);

        configurer.favorParameter(false)
                .ignoreAcceptHeader(false) // ignore override parameters in headers of request
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML);
    }
}
