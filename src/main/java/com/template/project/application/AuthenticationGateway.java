package com.template.project.application;

import com.template.project.application.dtos.request.AuthRequestDTO;
import com.template.project.application.dtos.request.RegisterRequestDTO;
import com.template.project.domain.usecases.impl.RegisterUserUseCaseImpl;
import com.template.project.domain.usecases.ports.RegisterUserUseCasePort;
import com.template.project.infra.repositories.impl.UserRepositoryImpl;
import com.template.project.infra.shared.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/auth")
public class AuthenticationGateway {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationGateway.class);

//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private Token token;

    private RegisterUserUseCaseImpl registerUserUseCaseImpl;


    @PostMapping()
    public ResponseEntity authenticate(@RequestBody AuthRequestDTO authForm) {
        try {
            //var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authForm.email(), authForm.password()));
            //var tokenGenerated = token.generateTokenJwt(auth);
            //return ResponseEntity.ok().body(new AuthenticationResponseDTO(tokenGenerated, "Bearer"));
           return ResponseEntity.ok().body(Map.of("name", "pedro"));
        } catch(AuthenticationException ex) {
            log.error("Error to try authenticate: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body, UriComponentsBuilder uriBuilder) {
        try {
            var user = registerUserUseCaseImpl.execute(new RegisterUserUseCasePort.Input(body.name(), body.email(), body.password()));
            var uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
            return ResponseEntity.created(uri).body(user);
        }catch (Exception ex) {
            log.error("Error to try save user", ex);
            ResponseEntity.badRequest().build();
        }

    }
}
