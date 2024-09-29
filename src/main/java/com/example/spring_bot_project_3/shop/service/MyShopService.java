package com.example.spring_bot_project_3.shop.service;

import com.example.spring_bot_project_3.FileHandlerUtils;
import com.example.spring_bot_project_3.shop.dto.ShopDto;
import com.example.spring_bot_project_3.shop.entity.Shop;
import com.example.spring_bot_project_3.shop.entity.ShopOpenRequest;
import com.example.spring_bot_project_3.shop.repo.ShopOpenReqRepo;
import com.example.spring_bot_project_3.shop.repo.ShopRepository;
import com.example.spring_bot_project_3.user.AuthenticationFacade;
import com.example.spring_bot_project_3.user.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MyShopService {
    private final AuthenticationFacade authFacade;
    private final ShopRepository shopRepository;
    private final FileHandlerUtils fileHandlerUtils;
    private final ShopOpenReqRepo shopOpenReqRepo;

    public ShopDto myShop(){
        UserEntity user = authFacade.extractUser();
        Shop shop = shopRepository.findByOwner(user).orElseThrow();
        return ShopDto.fromEntity(shop, true);
    }
    @Transactional
    public ShopDto update(ShopDto shopDto){
        UserEntity user = authFacade.extractUser();
        Shop shop = shopRepository.findByOwner(user).orElseThrow();
        shop.setName(shopDto.getName());
        shop.setDescription(shopDto.getDescription());
        shop.setCategory(shopDto.getCategory());
        return ShopDto.fromEntity(shopRepository.save(shop), true);
    }
    @Transactional
    public void requestOpen(){
        UserEntity user = authFacade.extractUser();
        Shop shop = shopRepository.findByOwner(user).orElseThrow();
        if (!(
                shop.getName() != null &&
                        shop.getDescription() != null &&
                        shop.getCategory() != null &&
                        shop.getStatus() != Shop.Status.OPEN
                ))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (shopOpenReqRepo.existsByShopIdAndIsApprovedIsNull(shop.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        shopOpenReqRepo.save(ShopOpenRequest.builder().shop(shop).build());
    }
    @Transactional
    public void requestClose(ShopDto shopDto){
        UserEntity user = authFacade.extractUser();
        Shop shop = shopRepository.findByOwner(user).orElseThrow();
        if (shop.getStatus() != Shop.Status.OPEN)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        shop.setCloseReason(shopDto.getCloseReason());
        shopRepository.save(shop);
    }
}
