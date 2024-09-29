package com.example.spring_bot_project_3.user;

import com.example.spring_bot_project_3.FileHandlerUtils;
import com.example.spring_bot_project_3.admin.dto.UserUpgradeDto;
import com.example.spring_bot_project_3.jwt.JwtTokenUtils;
import com.example.spring_bot_project_3.user.dto.CreateUserDto;
import com.example.spring_bot_project_3.user.dto.*;
import com.example.spring_bot_project_3.user.entity.MarketUserDetails;
import com.example.spring_bot_project_3.user.entity.UserEntity;
import com.example.spring_bot_project_3.user.entity.UserUpgrade;
import com.example.spring_bot_project_3.user.repo.UserRepository;
import com.example.spring_bot_project_3.user.repo.UserUpgradeRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final UserUpgradeRepo userUpgradeRepo;

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

    public UserDto updateUser(UpdateUserDto dto) {
        UserEntity userEntity = authFacade.extractUser();
        userEntity.setNickname(dto.getNickname());
        userEntity.setName(dto.getName());
        userEntity.setAge(dto.getAge());
        userEntity.setPhone(dto.getPhone());
        userEntity.setEmail(dto.getEmail());
        if (
                userEntity.getNickname() != null &&
                        userEntity.getName() != null &&
                        userEntity.getAge() != null &&
                        userEntity.getEmail() != null &&
                        userEntity.getPhone() != null &&
                        userEntity.getRoles().equals("ROLE_DEFAULT")
        )
            userEntity.setRoles("ROLE_USER");
        return UserDto.fromEntity(userRepository.save(userEntity));
    }

    public UserDto profileImgUpload(MultipartFile file) {
        UserEntity userEntity = authFacade.extractUser();
        String requestPath = fileHandlerUtils.saveFile(
                String.format("users/%d/", userEntity.getId()),"profile",file
        );
        userEntity.setProfileImg(requestPath);
        return UserDto.fromEntity(userRepository.save(userEntity));
    }
   public UserDto getUserInfo(){
        return UserDto.fromEntity(authFacade.extractUser());
   }

   public void upgradeRoleRequest(){
        UserEntity userEntity = authFacade.extractUser();
        if(userEntity.getRoles().contains("ROLE_BUSINESS"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        userUpgradeRepo.save(UserUpgrade.builder().target(userEntity).build());
   }



}
