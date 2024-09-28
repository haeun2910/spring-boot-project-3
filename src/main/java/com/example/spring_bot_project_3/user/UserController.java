package com.example.spring_bot_project_3.user;

import com.example.spring_bot_project_3.user.dto.CreateUserDto;
import com.example.spring_bot_project_3.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("signup")
    public UserDto signup(@RequestBody CreateUserDto dto) {
        return userService.createUser(dto);
    }
}
