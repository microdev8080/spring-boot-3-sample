package org.cham.authservice.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class LoginRequest {
    private String username;
    private String password;
}
