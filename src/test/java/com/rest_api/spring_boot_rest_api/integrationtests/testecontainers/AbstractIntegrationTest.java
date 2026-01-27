package com.rest_api.spring_boot_rest_api.integrationtests.testecontainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.mysql.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class) // static class for create container instance of database
public class AbstractIntegrationTest {
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static MySQLContainer mySQLContainer = new MySQLContainer("mysql:9.1.0"); // docker image name

        private static void startContainers() {
            Startables.deepStart(Stream.of(mySQLContainer)).join(); // put parameter in container if not present use default
        }

        private static Map<String, String> createConnectionConfiguration() {
            return Map.of(
                "spring.datasource.url", mySQLContainer.getJdbcUrl(),
                "spring.datasource.username", mySQLContainer.getUsername(),
                "spring.datasource.password", mySQLContainer.getPassword()
            );
        }

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment environment = applicationContext.getEnvironment(); // environment variables
            MapPropertySource testContainers = new MapPropertySource("testcontainers",
                    (Map) createConnectionConfiguration());
            environment.getPropertySources().addFirst(testContainers); // add first this configurations
        }

    }
}
