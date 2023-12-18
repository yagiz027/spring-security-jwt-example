package com.yagizeris.springsecurityjwtexample.common.util.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthenticationResponse {
    private String token;
    private String response;
}
