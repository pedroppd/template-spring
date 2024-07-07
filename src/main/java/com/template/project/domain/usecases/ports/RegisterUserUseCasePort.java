package com.template.project.domain.usecases.ports;

import com.template.project.infra.entities.UserEntity;

public interface RegisterUserUseCasePort {

    UserEntity execute(Input input);

    record Input(String userName, String email, String password) {}
}
