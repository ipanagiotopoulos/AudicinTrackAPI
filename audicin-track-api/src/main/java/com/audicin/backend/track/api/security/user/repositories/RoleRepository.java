package com.audicin.backend.track.api.security.user.repositories;


import com.audicin.backend.track.api.security.user.models.Role;
import com.audicin.backend.track.api.security.user.models.RoleEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}


