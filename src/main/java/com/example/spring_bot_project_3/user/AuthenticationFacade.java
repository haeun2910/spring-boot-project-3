package com.example.spring_bot_project_3.user;

import com.example.spring_bot_project_3.user.entity.MarketUserDetails;
import com.example.spring_bot_project_3.user.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    public UserEntity extractUser() {
        MarketUserDetails userDetails = (MarketUserDetails) getAuthentication().getPrincipal();
        return userDetails.getEntity();
    }
}
