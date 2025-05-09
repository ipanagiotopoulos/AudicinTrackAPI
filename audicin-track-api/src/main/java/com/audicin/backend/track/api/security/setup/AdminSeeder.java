package com.audicin.backend.track.api.security.setup;

import com.audicin.backend.track.api.security.dtos.request.RegisterUserDto;
import com.audicin.backend.track.api.security.user.models.Role;
import com.audicin.backend.track.api.security.user.models.RoleEnum;
import com.audicin.backend.track.api.security.user.models.User;
import com.audicin.backend.track.api.security.user.repositories.UserRepository;
import com.audicin.backend.track.api.security.user.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${audicin.api.admin.fullname}")
    private String adminFullName;

    @Value("${audicin.api.admin.password}")
    private String adminPassword;

    @Value("${audicin.api.admin.email}")
    private String adminEmail;

    @Autowired
    private Environment env;

    @Autowired
    public AdminSeeder(RoleRepository roleRepository,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(
            ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        RegisterUserDto userDto =
                RegisterUserDto.builder().fullName(adminFullName)
                        .email(adminEmail).password(adminPassword)
                        .email(adminEmail).build();
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);
        Optional<User> optionalUser =
                userRepository.findByEmail(userDto.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        User user = User.builder().fullName(userDto.getFullName())
                .email(userDto.getEmail()).role(optionalRole.get())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();

        User user1 = userRepository.save(user);
        System.out.println("Admin number 1 saved" + user1.toString());
    }
}
