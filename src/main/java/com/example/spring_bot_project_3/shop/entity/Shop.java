package com.example.spring_bot_project_3.shop.entity;

import ch.qos.logback.core.status.Status;
import com.example.spring_bot_project_3.entity.BaseEntity;
import com.example.spring_bot_project_3.user.entity.UserEntity;
import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Shop extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity owner;
    @Setter
    private String name;
    @Setter
    private String description;
    @Setter
    @Enumerated(EnumType.STRING)
    private Category category;
    @Setter
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.PREPARING;

    @Setter
    private String closeReason;
    public enum Category{
        FASHION, ELECTRONICS, HOME, BEAUTY, SPORTS, TOYS, HEALTH, FOOD, BOOKS, BABY
    }

    public enum Status{
        PREPARING, REJECTED, OPEN, CLOSED
    }
}
