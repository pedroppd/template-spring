package com.template.project.domain.usecases.impl;

import com.template.project.domain.usecases.ports.RegisterUserUseCasePort;
import com.template.project.infra.entities.UserEntity;
import com.template.project.infra.repositories.impl.UserRepositoryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import static java.lang.String.format;
import static java.util.Objects.isNull;

public class RegisterUserUseCaseImpl implements RegisterUserUseCasePort {

    private final UserRepositoryImpl userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCaseImpl(UserRepositoryImpl userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity execute(Input input) {
        var user = userRepository.findByEmail(input.email());
        if (user.isEmpty()) {
            var newUser = new UserEntity();
            newUser.setPassword(passwordEncoder.encode(input.password()));
            newUser.setEmail(input.email());
            newUser.setUserName(input.userName());
            return this.userRepository.save(newUser);
        }
        throw new RuntimeException(format("User %s already exists !", input.email()));
    }

}
