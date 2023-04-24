package org.cham.authservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class KeyCloakAuthRequest {
    private String grant_type;
    private String scope;
    private String username;
    private String password;
}
