package com.example.spring_bot_project_3.shop.dto;

import com.example.spring_bot_project_3.shop.entity.Shop;
import com.example.spring_bot_project_3.shop.entity.ShopOpenRequest;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShopOpenReqDto {
    private Long id;
    private Long shopId;
    private String shopName;
    private Boolean isApproved;
    private String reason;

    public static ShopOpenReqDto fromEntity(ShopOpenRequest entity) {
        return ShopOpenReqDto.builder()
                .id(entity.getId())
                .shopId(entity.getShop().getId())
                .shopName(entity.getShop().getName())
                .isApproved(entity.getIsApproved())
                .reason(entity.getReason())
                .build();
    }

}
