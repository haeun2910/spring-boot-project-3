package com.example.spring_bot_project_3.admin;

import com.example.spring_bot_project_3.admin.dto.UserUpgradeDto;
import com.example.spring_bot_project_3.shop.dto.ShopDto;
import com.example.spring_bot_project_3.shop.dto.ShopOpenReqDto;
import com.example.spring_bot_project_3.shop.entity.Shop;
import com.example.spring_bot_project_3.shop.entity.ShopOpenRequest;
import com.example.spring_bot_project_3.shop.repo.ShopOpenReqRepo;
import com.example.spring_bot_project_3.shop.repo.ShopRepository;
import com.example.spring_bot_project_3.user.dto.UserDto;
import com.example.spring_bot_project_3.user.entity.UserEntity;
import com.example.spring_bot_project_3.user.entity.UserUpgrade;
import com.example.spring_bot_project_3.user.repo.UserRepository;
import com.example.spring_bot_project_3.user.repo.UserUpgradeRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final UserUpgradeRepo userUpgradeRepo;
    private final ShopRepository shopRepository;
    private final ShopOpenReqRepo shopOpenReqRepo;

    public  Page<UserDto> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDto::fromEntity);
    }

    public Page<UserUpgradeDto> listRequests(Pageable pageable) {
        return userUpgradeRepo.findAll(pageable)
                .map(UserUpgradeDto::fromEntity);
    }

    @Transactional
    public UserUpgradeDto approveUpgrade(Long id){
        UserUpgrade userUpgrade = userUpgradeRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        userUpgrade.setApproved(true);
        if (!userUpgrade.getTarget().getRoles().contains("ROLE_BUSINESS")){
            shopRepository.save(Shop.builder().owner(userUpgrade.getTarget()).build());
            userUpgrade.getTarget().setRoles("ROLE_USER,ROLE_BUSINESS");
        }
        return UserUpgradeDto.fromEntity(userUpgradeRepo.save(userUpgrade));
    }
    @Transactional
    public UserUpgradeDto rejectUpgrade(Long id){
        UserUpgrade upgrade = userUpgradeRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        upgrade.setApproved(false);
        return UserUpgradeDto.fromEntity(upgrade);
    }

    public Page<ShopDto> getShopOpenReq(Pageable pageable) {
        return shopRepository.findOpenRequested(pageable).map(ShopDto::fromEntity);
    }
    public Page<ShopDto> getShopCLoseReq(Pageable pageable) {
        return shopRepository.findCloseRequested(Shop.Status.CLOSED, pageable).map(shop -> ShopDto.fromEntity(shop, true));
    }

    public void approveOpen(Long shopId){
        ShopOpenRequest openRequest = shopOpenReqRepo.findTopByShopIdAndIsApprovedIsNullOrderByCreatedAtDesc(shopId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        openRequest.setIsApproved(true);
        openRequest.getShop().setStatus(Shop.Status.OPEN);
        shopOpenReqRepo.save(openRequest);
    }
    public void rejectOpen(Long shopId, ShopOpenReqDto reqDto){
        ShopOpenRequest openRequest = shopOpenReqRepo.findTopByShopIdAndIsApprovedIsNullOrderByCreatedAtDesc(shopId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        openRequest.setIsApproved(false);
        openRequest.setReason(reqDto.getReason());
        openRequest.getShop().setStatus(Shop.Status.REJECTED);
        shopOpenReqRepo.save(openRequest);
    }
    public void approveClose(Long shopId){
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (shop.getCloseReason() == null || shop.getStatus() != Shop.Status.OPEN){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        shop.setStatus(Shop.Status.CLOSED);
        shopRepository.save(shop);
    }
}
