//package com.healthcare.healthcare_system.model;
//
//public class user {
//}
package com.healthcare.healthcare_system.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Name cannot be empty")
    @Column(nullable=false)
    private String name;

    @NotBlank(message="Email cannot be empty")
    @Email(message="Email should be valid")
    @Column(nullable=false,unique=true)
    private String email;

    @NotBlank(message="Role cannot be empty")
    @Column(nullable=false)
    private String role; // CITIZEN / ADMIN / DOCTOR

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message="Password cannot be empty")
    @Size(min=6, message="Password must be at least 6 characters")
    @Column(nullable=false)
    private String password;

    // Constructors
    public User() {}

    public User(String name, String email, String role, String password) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.password=password;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password= password;
    }


    public void orElseThrow(Object userNotFound) {
        return;
    }
}
