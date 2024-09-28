package com.example.spring_bot_project_3.user;

import com.example.spring_bot_project_3.FileHandlerUtils;
import com.example.spring_bot_project_3.jwt.JwtTokenUtils;
import com.example.spring_bot_project_3.user.dto.CreateUserDto;
import com.example.spring_bot_project_3.user.dto.*;
import com.example.spring_bot_project_3.user.entity.MarketUserDetails;
import com.example.spring_bot_project_3.user.entity.UserEntity;
import com.example.spring_bot_project_3.user.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final AuthenticationFacade authFacade;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final FileHandlerUtils fileHandlerUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(MarketUserDetails::fromEntity)
                .orElseThrow(() -> new UsernameNotFoundException("not found username"));
    }
    @Transactional
    public UserDto createUser(CreateUserDto dto) {
        if (!dto.getPassword().equals(dto.getPassCheck()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (userRepository.existsByUsername(dto.getUsername()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return UserDto.fromEntity(userRepository.save(UserEntity.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build()));
    }
}
