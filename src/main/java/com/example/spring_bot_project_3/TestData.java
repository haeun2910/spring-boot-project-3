package com.example.spring_bot_project_3;

import com.example.spring_bot_project_3.user.entity.UserEntity;
import com.example.spring_bot_project_3.user.repo.UserRepository;
import com.example.spring_bot_project_3.user.repo.UserUpgradeRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TestData {
    private final UserRepository userRepository;
    private final UserUpgradeRepo userUpgradeRepository;
    private final PasswordEncoder passwordEncoder;

    public TestData(UserRepository userRepository, UserUpgradeRepo userUpgradeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userUpgradeRepository = userUpgradeRepository;
        this.passwordEncoder = passwordEncoder;
//        testUser();
    }
    private void testUser() {
//        UserEntity admin = UserEntity.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("test"))
//                .roles("ROLE_USER,ROLE_ADMIN")
//                .build();
//        userRepository.save(admin);

    }
}
