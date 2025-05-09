package com.audicin.backend.track.api.security.services;


import com.audicin.backend.track.api.security.dtos.request.LoginUserDto;
import com.audicin.backend.track.api.security.dtos.request.RegisterUserDto;
import com.audicin.backend.track.api.security.dtos.response.RegisteredUserResponseDto;
import com.audicin.backend.track.api.security.user.models.Role;
import com.audicin.backend.track.api.security.user.models.RoleEnum;
import com.audicin.backend.track.api.security.user.models.User;
import com.audicin.backend.track.api.security.user.repositories.RoleRepository;
import com.audicin.backend.track.api.security.user.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 AuthenticationManager authenticationManager,
                                 PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisteredUserResponseDto signup(RegisterUserDto input) {
        Optional<Role> optionalRole =
                roleRepository.findByName(RoleEnum.PARTNER);

        if (optionalRole.isEmpty()) {
            return null;
        }

        User user = User.builder().fullName(input.getFullName())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .role(optionalRole.get()).build();
        User savedUser = userRepository.save(user);
        RegisteredUserResponseDto userResponse =
                RegisteredUserResponseDto.builder().email(savedUser.getEmail())
                        .fullName(savedUser.getEmail()).build();
        return userResponse;
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getEmail(),
                        input.getPassword()));

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}

