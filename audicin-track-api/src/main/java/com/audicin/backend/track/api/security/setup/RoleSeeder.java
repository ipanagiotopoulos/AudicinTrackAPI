package com.audicin.backend.track.api.security.setup;

import com.audicin.backend.track.api.security.user.models.Role;
import com.audicin.backend.track.api.security.user.repositories.RoleRepository;
import com.audicin.backend.track.api.security.user.models.RoleEnum;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;


    public RoleSeeder(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(
            @NotNull final ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {

        RoleEnum[] roleNames =
                new RoleEnum[] {RoleEnum.PARTNER, RoleEnum.ADMIN};
        Map<RoleEnum,String> roleDescriptionMap =
                Map.of(RoleEnum.PARTNER, "Partner role", RoleEnum.ADMIN,
                        "Administrator role"

                );

        Arrays.stream(roleNames).forEach(roleName->{
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out::println, ()->{
                Role roleToCreate = Role.builder().name(roleName)
                        .description(roleDescriptionMap.get(roleName)).build();
                roleRepository.save(roleToCreate);
            });
        });
    }
}

