package com.yagizeris.springsecurityjwtexample.business.abstracts;

import com.yagizeris.springsecurityjwtexample.business.dto.requests.update.UpdateUserRequest;
import com.yagizeris.springsecurityjwtexample.business.dto.responses.get.GetUserListResponse;
import com.yagizeris.springsecurityjwtexample.business.dto.responses.get.GetUserResponse;
import com.yagizeris.springsecurityjwtexample.business.dto.responses.update.UpdateUserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<GetUserListResponse> getUserList();
    GetUserResponse getUserById(UUID id);
    UpdateUserResponse updateUser(UUID id, UpdateUserRequest request);
    void deleteById(UUID id);
}
