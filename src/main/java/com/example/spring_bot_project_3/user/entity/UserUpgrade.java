package com.example.spring_bot_project_3.user.entity;

import com.example.spring_bot_project_3.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role_upgrade-request")
public class UserUpgrade extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity target;

    @Setter
    private Boolean approved;
}
