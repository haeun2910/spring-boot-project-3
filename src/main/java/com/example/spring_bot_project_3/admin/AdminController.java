package com.example.spring_bot_project_3.admin;

import com.example.spring_bot_project_3.admin.dto.UserUpgradeDto;
import com.example.spring_bot_project_3.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("upgrade-req")
    public Page<UserUpgradeDto> upgradeRequests(Pageable pageable) {
        return adminService.listRequests(pageable);
    }
}
