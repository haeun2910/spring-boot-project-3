package com.example.spring_bot_project_3.user.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketUserDetails implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private String age;
    private String email;
    private String phone;
    private String profileImg;
    private String rolesRaw;
    @Getter
    private UserEntity entity;

    public static MarketUserDetails fromEntity(UserEntity entity) {
        return MarketUserDetails.builder().entity(entity).build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(entity.getRoles().split(","))
                .map(role -> (GrantedAuthority) () -> role)
                .toList();
    }

    @Override
    public String getPassword() {
        return this.entity.getPassword();
    }

    @Override
    public String getUsername() {
        return this.entity.getUsername();
    }
    public Long getId() {
        return this.entity.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
