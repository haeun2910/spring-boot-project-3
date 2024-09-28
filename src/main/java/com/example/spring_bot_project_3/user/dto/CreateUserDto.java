package com.example.spring_bot_project_3.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
    private String username;
    private String password;
    private String passCheck;
}
