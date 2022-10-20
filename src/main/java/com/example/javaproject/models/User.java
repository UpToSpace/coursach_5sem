package com.example.javaproject.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @Column
    @NotEmpty(message = "Fill field username, please")
    @Size(min = 3, max = 20, message = "Username should be 3-20 characters long")
    @Email(message = "")
    private String email;

    @Column
    @NotEmpty(message = "Fill field username, please")
    @Size(min = 3, max = 20, message = "Username should be 3-20 characters long")
    private String username;

    @Column
    @NotEmpty(message = "Fill field username, please")
    @Size(min = 3, max = 20, message = "Username should be 3-20 characters long")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Override
    public String toString() {
        return "--User-- \nUsername: "
                + this.username
                + "\nEmail:" + this.email
                + "\nPassword" + this.password
                + "\nRole" + this.role;
    }
}
