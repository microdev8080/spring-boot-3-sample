package org.cham.authservice.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class KeyCloakAuthRequest {
    private String grant_type;
    private String scope;
    private String username;
    private String password;
}
