package org.cham.authservice.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class LoginResponse {
    private String accessToken;
    private int code;
    private String message;
}
