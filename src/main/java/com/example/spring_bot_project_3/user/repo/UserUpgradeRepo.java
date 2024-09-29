package com.example.spring_bot_project_3.user.repo;

import com.example.spring_bot_project_3.user.entity.UserUpgrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserUpgradeRepo extends JpaRepository<UserUpgrade, Long> {
}
