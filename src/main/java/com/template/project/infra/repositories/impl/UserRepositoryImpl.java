package com.template.project.infra.repositories.impl;

import com.template.project.infra.entities.UserEntity;
import com.template.project.infra.repositories.ports.SpringUserRepository;

import java.util.Optional;


public class UserRepositoryImpl {

    private SpringUserRepository springUserRepository;

    public UserRepositoryImpl(SpringUserRepository springUserRepository) {
        this.springUserRepository = springUserRepository;
    }

    public Optional<UserEntity> findByEmail(String email) {
        return springUserRepository.findByEmail(email);
    }

    public UserEntity findByUuid(String uuid) {
        return springUserRepository.findById(uuid).orElse(null);
    }

    public UserEntity save(UserEntity userEntity) {
        return springUserRepository.save(userEntity);
    }
}
