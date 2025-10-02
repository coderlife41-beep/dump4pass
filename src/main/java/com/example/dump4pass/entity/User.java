package com.example.dump4pass.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") // "user" is reserved keyword in postgres
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;  // store encrypted in real app

}
