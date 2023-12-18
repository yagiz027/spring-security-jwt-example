package com.yagizeris.springsecurityjwtexample.business.rules;

import com.yagizeris.springsecurityjwtexample.common.exceptions.BusinessException;
import com.yagizeris.springsecurityjwtexample.common.util.exceptionUtil.constants.Messages;
import com.yagizeris.springsecurityjwtexample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserBusinessRules {
    private final UserRepository repository;

    public void checkIfUserNotExists(UUID id){
        if(!repository.existsById(id)){
            throw new BusinessException(Messages.User.NOT_EXISTS);
        }
    }

    public void checkIfEmailAlreadyExists(String email){
        if(repository.existsByEmail(email)){
            throw new BusinessException(Messages.User.USER_ALREADY_EXISTS_EMAIL);
        }
    }

    public void checkIfUsernameAlreadyExists(String username){
        if(repository.existsByUsernameIgnoreCase(username)){
            throw new BusinessException(Messages.User.USER_ALREADY_EXISTS_USERNAME);
        }
    }
}
