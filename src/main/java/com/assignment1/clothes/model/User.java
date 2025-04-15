package com.assignment1.clothes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.*;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role; // e.g., "USER", "ADMIN"

    public enum Role {
        ADMIN, USER, WAREHOUSE
    }

}
