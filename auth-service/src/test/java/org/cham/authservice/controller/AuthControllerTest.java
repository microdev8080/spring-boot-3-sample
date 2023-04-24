package org.cham.authservice.controller;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.cham.authservice.controller.AuthController;
import org.cham.authservice.dto.LoginRequest;
import org.cham.authservice.dto.LoginResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
public class AuthControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private AuthController authController;

    static final KeycloakContainer keycloak;

    static {
        keycloak = new KeycloakContainer().withRealmImportFile("keycloak/realm-export.json");
        keycloak.start();
    }

    @PostConstruct
    public void init() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @DynamicPropertySource
    static void registerResourceServerIssuerProperty(DynamicPropertyRegistry registry) {
        registry.add("app.config.keycloak.auth-url", () -> keycloak.getAuthServerUrl() + "realms/micro-dev/protocol/openid-connect/token");
        registry.add("app.config.keycloak.client-id", () -> "micro-dev-client");

    }

    @Test
    @DisplayName("Login Success Test")
    void login_success_test() {
        LoginRequest loginRequest = LoginRequest.builder().password("1qaz2wsx@").username("jdoe").build();
        LoginResponse loginResponse = authController.login(loginRequest);
        assertThat(loginResponse.getCode() == 200);
        assertThat(loginResponse.getAccessToken() != null);
        assertThat(loginResponse.getMessage().equalsIgnoreCase("Authenticated!"));
    }

    @Test
    @DisplayName("Login Fail Test On Invalid Password")
    void login_fail_test() {
        LoginRequest loginRequest = LoginRequest.builder().password("1qaz2wsx@1").username("jdoe").build();
        LoginResponse loginResponse = authController.login(loginRequest);
        assertThat(loginResponse.getCode() == 401);
        assertThat(loginResponse.getAccessToken() == null);
        assertThat(loginResponse.getMessage().equalsIgnoreCase("Un-Authenticated!"));
    }

}
