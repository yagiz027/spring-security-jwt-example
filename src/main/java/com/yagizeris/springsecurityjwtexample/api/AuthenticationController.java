package com.yagizeris.springsecurityjwtexample.api;

import com.yagizeris.springsecurityjwtexample.business.abstracts.AuthenticationService;
import com.yagizeris.springsecurityjwtexample.common.util.dto.AuthenticationRequest;
import com.yagizeris.springsecurityjwtexample.common.util.dto.AuthenticationResponse;
import com.yagizeris.springsecurityjwtexample.common.util.dto.RegisterRequest;
import com.yagizeris.springsecurityjwtexample.common.util.dto.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest){
        return authenticationService.register(registerRequest);
    }

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public AuthenticationResponse auth(@RequestBody AuthenticationRequest request){
        return authenticationService.authentication(request);
    }
}
