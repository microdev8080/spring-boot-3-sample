package org.cham.authservice.service;

import feign.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.cham.authservice.client.KeyCloakClient;
import org.cham.authservice.dto.KeyCloakAuthRequest;
import org.cham.authservice.dto.LoginRequest;
import org.cham.authservice.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.stereotype.Service;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {

    @Autowired
    private KeyCloakClient keyCloakClient;

    public LoginResponse login(LoginRequest loginRequest) {
        KeyCloakAuthRequest keyCloakAuthRequest = KeyCloakAuthRequest
                .builder()
                .username(loginRequest.getUsername())
                .password(loginRequest.getPassword())
                .scope("openid")
                .grant_type("password")
                .build();

        int code;
        String accessToken = null;
        String message = "Un-Authorized User!";

        try (Response keyCloakResponse = keyCloakClient.authenticate(keyCloakAuthRequest)) {

            code = keyCloakResponse.status();
            if (code == HttpStatus.SC_OK) {
                String responseStr = IOUtils.toString(keyCloakResponse.body().asInputStream(), StandardCharsets.UTF_8);
                JacksonJsonParser jsonParser = new JacksonJsonParser();
                accessToken = jsonParser.parseMap(responseStr).get("access_token").toString();
                message = "Authenticated!";
            } else {
                code = HttpStatus.SC_UNAUTHORIZED;
            }

        } catch (Exception e) {
            log.error("An error Occurred while Authenticating User : {}", loginRequest.getUsername(), e);
            message = "An Internal Error Occurred!";
            code = HttpStatus.SC_INTERNAL_SERVER_ERROR;
        }

        return LoginResponse.builder()
                .accessToken(accessToken)
                .code(code)
                .message(message)
                .build();
    }
}
