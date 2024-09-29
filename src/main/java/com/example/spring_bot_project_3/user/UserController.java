package com.example.spring_bot_project_3.user;

import com.example.spring_bot_project_3.user.dto.CreateUserDto;
import com.example.spring_bot_project_3.user.dto.UpdateUserDto;
import com.example.spring_bot_project_3.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("signup")
    public UserDto signup(@RequestBody CreateUserDto dto) {
        return userService.createUser(dto);
    }
    @PutMapping("update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserDto update(@RequestBody UpdateUserDto dto) {
        return userService.updateUser(dto);
    }
    @PutMapping(
            value = "profile-img",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public UserDto updateProfileImg(@RequestParam("file") MultipartFile file) {
        return userService.profileImgUpload(file);
    }
    @GetMapping("get-user-info")
    public UserDto getUserInfo(){
        return userService.getUserInfo();
    }

    @PutMapping("upgrade")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upgradeUser() {
        userService.upgradeRoleRequest();
    }

}
