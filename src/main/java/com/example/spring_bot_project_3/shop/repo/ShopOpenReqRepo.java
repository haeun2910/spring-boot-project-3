package com.example.spring_bot_project_3.shop.repo;

import com.example.spring_bot_project_3.shop.entity.ShopOpenRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopOpenReqRepo extends JpaRepository<ShopOpenRequest, Long> {
    Boolean existsByShopIdAndIsApprovedIsNull(Long shopId);
    Optional<ShopOpenRequest> findTopByShopIdAndIsApprovedIsNullOrderByCreatedAtDesc(Long shopId);
    Optional<ShopOpenRequest> findTopByShopIdAndIsApprovedIsFalseOrderByCreatedAtDesc(Long shopId);

}
