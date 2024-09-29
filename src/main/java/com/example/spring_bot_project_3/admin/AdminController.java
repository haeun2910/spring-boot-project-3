package com.example.spring_bot_project_3.admin;

import com.example.spring_bot_project_3.admin.dto.UserUpgradeDto;
import com.example.spring_bot_project_3.shop.dto.ShopDto;
import com.example.spring_bot_project_3.shop.dto.ShopOpenReqDto;
import com.example.spring_bot_project_3.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("users")
    public Page<UserDto> getUsers(Pageable pageable) {
        return adminService.getUsers(pageable);
    }

    @GetMapping("upgrade-req")
    public Page<UserUpgradeDto> upgradeRequests(Pageable pageable) {
        return adminService.listRequests(pageable);
    }

    @PutMapping("upgrade-req/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserUpgradeDto approve(@PathVariable Long id) {
        return adminService.approveUpgrade(id);
    }

    @DeleteMapping("upgrade-req/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserUpgradeDto rejectUpgrade(@PathVariable("id") Long id) {
        return adminService.rejectUpgrade(id);
    }

    @GetMapping("shops-open")
    public Page<ShopDto> getShopOpenReq(Pageable pageable) {
        return adminService.getShopOpenReq(pageable);

    }

    @GetMapping("shops-close")
    public Page<ShopDto> getShopCloseReq(Pageable pageable) {
        return adminService.getShopCLoseReq(pageable);
    }
    @PutMapping(
            value = "shops/{shopId}", params = "action=approve"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void approveShop(@PathVariable("shopId") Long shopId) {
        adminService.approveOpen(shopId);
    }
    @PutMapping(
            value = "shops/{shopId}", params = "action=disapprove"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rejectShop(@PathVariable("shopId") Long shopId, @RequestBody ShopOpenReqDto dto) {
        adminService.rejectOpen(shopId, dto);
    }
    @DeleteMapping("shops/{shopId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void approveClose(@PathVariable("shopId") Long shopId) {
        adminService.approveClose(shopId);
    }
}



