package com.example.spring_bot_project_3.admin;

import com.example.spring_bot_project_3.admin.dto.UserUpgradeDto;
import com.example.spring_bot_project_3.user.entity.UserEntity;
import com.example.spring_bot_project_3.user.repo.UserRepository;
import com.example.spring_bot_project_3.user.repo.UserUpgradeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final UserUpgradeRepo userUpgradeRepo;


    public Page<UserUpgradeDto> listRequests(Pageable pageable) {
        return userUpgradeRepo.findAll(pageable)
                .map(UserUpgradeDto::fromEntity);
    }
}
