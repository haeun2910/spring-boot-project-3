package com.example.spring_bot_project_3.shop.repo;


import com.example.spring_bot_project_3.shop.entity.Shop;
import com.example.spring_bot_project_3.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    Optional<Shop> findByOwner(UserEntity user);
    @Query("SELECT DISTINCT s " +
            "FROM ShopOpenRequest r JOIN r.shop s " +
            "WHERE r.isApproved IS NULL")
    Page<Shop> findOpenRequested(Pageable pageable);
    @Query("SELECT s " +
            "FROM Shop s " +
            "WHERE s.closeReason IS NOT NULL " +
            "AND s.status != :status")
    Page<Shop> findCloseRequested(Shop.Status status, Pageable pageable);
}
