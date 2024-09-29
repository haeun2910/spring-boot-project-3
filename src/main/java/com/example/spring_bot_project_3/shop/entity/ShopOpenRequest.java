package com.example.spring_bot_project_3.shop.entity;

import com.example.spring_bot_project_3.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShopOpenRequest extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Shop shop;
    @Setter
    private Boolean isApproved;
    @Setter
    private String reason;
}
