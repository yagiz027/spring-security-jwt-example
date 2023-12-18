package com.yagizeris.springsecurityjwtexample.api;

import com.yagizeris.springsecurityjwtexample.business.abstracts.UserService;
import com.yagizeris.springsecurityjwtexample.business.dto.requests.update.UpdateUserRequest;
import com.yagizeris.springsecurityjwtexample.business.dto.responses.get.GetUserListResponse;
import com.yagizeris.springsecurityjwtexample.business.dto.responses.get.GetUserResponse;
import com.yagizeris.springsecurityjwtexample.business.dto.responses.update.UpdateUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<GetUserListResponse> getUserList(){
        return userService.getUserList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public GetUserResponse getUserById(@PathVariable UUID id){
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UpdateUserResponse update(@PathVariable UUID id, @RequestBody UpdateUserRequest request){
        return userService.updateUser(id,request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID id){
        userService.deleteById(id);
    }
}
