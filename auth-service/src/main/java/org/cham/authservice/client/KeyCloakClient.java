package org.cham.authservice.client;

import feign.Response;
import org.cham.authservice.config.FeignClientConfiguration;
import org.cham.authservice.dto.KeyCloakAuthRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "keycloak", url = "${app.config.keycloak.auth-url}", configuration = FeignClientConfiguration.class)
public interface KeyCloakClient {
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Response authenticate(@RequestBody KeyCloakAuthRequest keyCloakAuthRequest);
}
