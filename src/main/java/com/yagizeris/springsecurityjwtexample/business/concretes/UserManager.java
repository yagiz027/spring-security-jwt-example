package com.yagizeris.springsecurityjwtexample.business.concretes;

import com.yagizeris.springsecurityjwtexample.business.rules.UserBusinessRules;
import com.yagizeris.springsecurityjwtexample.business.dto.requests.update.UpdateUserRequest;
import com.yagizeris.springsecurityjwtexample.business.dto.responses.get.GetUserListResponse;
import com.yagizeris.springsecurityjwtexample.business.dto.responses.get.GetUserResponse;
import com.yagizeris.springsecurityjwtexample.business.dto.responses.update.UpdateUserResponse;
import com.yagizeris.springsecurityjwtexample.common.modelMapper.ModelMapperService;
import com.yagizeris.springsecurityjwtexample.entity.User;
import com.yagizeris.springsecurityjwtexample.entity.enums.RoleEnum;
import com.yagizeris.springsecurityjwtexample.repository.UserRepository;
import com.yagizeris.springsecurityjwtexample.business.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserManager implements UserService {

    private final UserRepository userRepository;

    private final ModelMapperService mapperService;

    private final UserBusinessRules rules;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<GetUserListResponse> getUserList() {
        return userRepository.findAll()
                .stream().map(user -> mapperService.forResponse().map(user,GetUserListResponse.class)).toList();
    }

    @Override
    public GetUserResponse getUserById(UUID id) {
        rules.checkIfUserNotExists(id);
        User user = userRepository.findById(id).orElseThrow();
        return mapperService.forResponse().map(user,GetUserResponse.class);
    }

    @Override
    public UpdateUserResponse updateUser(UUID id, UpdateUserRequest request) {
        rules.checkIfUserNotExists(id);
        rules.checkIfUsernameAlreadyExists(request.getUsername());
        rules.checkIfEmailAlreadyExists(request.getEmail());
        User oldUser = mapperService.forRequest().map(getUserById(id),User.class);
        User user = mapperService.forRequest().map(request,User.class);
        user.setId(id);
        user.setRole(RoleEnum.USER);
        user.setCreatedAt(oldUser.getCreatedAt());
        user.setUpdatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return mapperService.forResponse().map(user,UpdateUserResponse.class);
    }

    @Override
    public void deleteById(UUID id) {
        rules.checkIfUserNotExists(id);
        userRepository.deleteById(id);
    }
}
