package com.yagizeris.springsecurityjwtexample.business.concretes;

import com.yagizeris.springsecurityjwtexample.business.abstracts.AuthenticationService;
import com.yagizeris.springsecurityjwtexample.business.rules.UserBusinessRules;
import com.yagizeris.springsecurityjwtexample.common.modelMapper.ModelMapperService;
import com.yagizeris.springsecurityjwtexample.common.util.dto.AuthenticationRequest;
import com.yagizeris.springsecurityjwtexample.common.util.dto.AuthenticationResponse;
import com.yagizeris.springsecurityjwtexample.common.util.dto.RegisterRequest;
import com.yagizeris.springsecurityjwtexample.common.util.dto.RegisterResponse;
import com.yagizeris.springsecurityjwtexample.common.util.exceptionUtil.constants.Messages;
import com.yagizeris.springsecurityjwtexample.common.util.securityUtil.JwtService;
import com.yagizeris.springsecurityjwtexample.entity.User;
import com.yagizeris.springsecurityjwtexample.entity.enums.RoleEnum;
import com.yagizeris.springsecurityjwtexample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserAuthenticationManager implements AuthenticationService {
    private final UserRepository repository;
    private final ModelMapperService mapperService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserBusinessRules rules;
    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        rules.checkIfEmailAlreadyExists(registerRequest.getEmail());
        rules.checkIfUsernameAlreadyExists(registerRequest.getUsername());
        User user = mapperService.forRequest().map(registerRequest,User.class);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(RoleEnum.USER);
        user.setCreatedAt(LocalDateTime.now());
        repository.save(user);
        RegisterResponse response = mapperService.forResponse().map(user,RegisterResponse.class);
        response.setResponse(Messages.Authentication.REGISTER_SUCCESSFUL);
        return response;
    }

    @Override
    public AuthenticationResponse authentication(AuthenticationRequest authRequest) {
        rules.checkIfEmailAlreadyExists(authRequest.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword()));
        var user = repository.findByEmail(authRequest.getEmail());
        HashMap<String,Object> payload = new HashMap<>();
        for(var role : user.getAuthorities()){
            payload.put(Messages.JwtPayload.ROLES,role.toString());
        }
        payload.put(Messages.JwtPayload.EMAIL,authRequest.getEmail());
        var jwt=jwtService.generateToken(payload,user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .response(Messages.Authentication.AUTH_SUCCESSFUL)
                .build();
    }
}
