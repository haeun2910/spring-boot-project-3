package com.example.spring_bot_project_3.config;

import com.example.spring_bot_project_3.jwt.JwtTokenFilter;
import com.example.spring_bot_project_3.jwt.JwtTokenUtils;
import com.example.spring_bot_project_3.oauth.OAuth2UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class WebSecurityConfig {
    private final JwtTokenUtils tokenUtils;
    private final UserDetailsService userService;
    private final OAuth2UserServiceImpl oAuth2UserService;
    public WebSecurityConfig(
            JwtTokenUtils tokenUtils,
            UserDetailsService userService,
            OAuth2UserServiceImpl oAuth2UserService
    ) {
        this.tokenUtils = tokenUtils;
        this.userService = userService;
        this.oAuth2UserService = oAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/error", "/token/issue", "/test/**")
                            .permitAll();
                    auth.requestMatchers("/users/signup","/users/signin").anonymous();
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(
                        new JwtTokenFilter(
                                tokenUtils,
                                userService
                        ),
                        AuthorizationFilter.class
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/views/login")
                        .defaultSuccessUrl("/views/signin-home-page")
                        .failureUrl("/users/login?fail")
                        .permitAll())
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/views/login")
                        .defaultSuccessUrl("/views/signin-home-page")
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/views/logout")
                        .logoutSuccessUrl("/views/login")

                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
