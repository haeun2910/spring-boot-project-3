package com.example.spring_bot_project_3.shop.controller;

import com.example.spring_bot_project_3.shop.dto.ShopDto;
import com.example.spring_bot_project_3.shop.service.MyShopService;
import com.example.spring_bot_project_3.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shops/my-shop")
@RequiredArgsConstructor
public class MyShopController {
    private final MyShopService myShopService;

    @GetMapping
    public ShopDto getMyShop() {
        return myShopService.myShop();
    }
    @PutMapping
    public ShopDto update(@RequestBody ShopDto shopDto) {
        return myShopService.update(shopDto);
    }
    @PutMapping("open")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void openShop() {
        myShopService.requestOpen();
    }
    @PutMapping("close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeShop(@RequestBody ShopDto shopDto) {
        myShopService.requestClose(shopDto);
    }
}
