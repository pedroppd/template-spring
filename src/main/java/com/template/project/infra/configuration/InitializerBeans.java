package com.template.project.infra.configuration;

import com.template.project.domain.usecases.impl.RegisterUserUseCaseImpl;
import com.template.project.infra.repositories.ports.SpringUserRepository;
import com.template.project.infra.repositories.impl.UserRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitializerBeans {

    @Bean
    public UserRepositoryImpl userRepository(SpringUserRepository springUserRepository) {
        return new UserRepositoryImpl(springUserRepository);
    }

    @Bean
    public RegisterUserUseCaseImpl registerUserUseCaseAdapter(UserRepositoryImpl userRepository,
                                                              PasswordEncoder passwordEncoder) {
        return new RegisterUserUseCaseImpl(userRepository, passwordEncoder);
    }
}
