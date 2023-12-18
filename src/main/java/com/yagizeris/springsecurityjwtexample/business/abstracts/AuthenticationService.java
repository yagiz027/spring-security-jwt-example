package com.yagizeris.springsecurityjwtexample.business.abstracts;

import com.yagizeris.springsecurityjwtexample.common.util.dto.AuthenticationRequest;
import com.yagizeris.springsecurityjwtexample.common.util.dto.AuthenticationResponse;
import com.yagizeris.springsecurityjwtexample.common.util.dto.RegisterRequest;
import com.yagizeris.springsecurityjwtexample.common.util.dto.RegisterResponse;

public interface AuthenticationService {
    RegisterResponse register(RegisterRequest registerRequest);
    AuthenticationResponse authentication(AuthenticationRequest authRequest);
}
