package com.template.project.infra.repositories.impl;

import com.template.project.infra.entities.UserEntity;
import com.template.project.infra.repositories.ports.SpringUserRepository;


public class UserRepositoryImpl {

    private SpringUserRepository springUserRepository;

    public UserRepositoryImpl(SpringUserRepository springUserRepository) {
        this.springUserRepository = springUserRepository;
    }

    public UserEntity findByEmail(String email) {
        return springUserRepository.findByEmail(email).orElse(null);
    }

    public UserEntity findByUuid(String uuid) {
        return springUserRepository.findById(uuid).orElse(null);
    }

    public UserEntity save(UserEntity userEntity) {
        return springUserRepository.save(userEntity);
    }
}
