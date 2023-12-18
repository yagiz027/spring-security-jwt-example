package com.yagizeris.springsecurityjwtexample.business.dto.responses.create;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserResponse {
    private UUID id;

    private String name;
    
    private String surname;
    
    private String email;
    
    private String username;
    
    private String password;
}
