package com.example.spring_bot_project_3.admin.dto;

import com.example.spring_bot_project_3.user.entity.UserUpgrade;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserUpgradeDto {
    private Long id;
    private String username;
    private Boolean approved;

    public static UserUpgradeDto fromEntity(UserUpgrade entity) {
        return UserUpgradeDto.builder()
                .id(entity.getId())
                .username(entity.getTarget().getUsername())
                .approved(entity.getApproved())
                .build();
    }
}
