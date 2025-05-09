package com.audicin.backend.track.api.security.user.repositories;

import com.audicin.backend.track.api.security.user.models.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    Optional<User> findByEmail(String email);
}

