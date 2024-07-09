package com.template.project.infra.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_name", length = 50, nullable = false)
    private String userName;

    @Column(unique = true, nullable = false)
    @Email(message = "Invalid Email !")
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

}
