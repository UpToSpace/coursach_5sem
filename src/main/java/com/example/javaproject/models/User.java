package com.example.javaproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    private Status status;

    @Override
    public String toString() {
        return "--User-- \nUsername: "
                + this.username
                + "\nEmail: " + this.email
                + "\nPassword: " + this.password
                + "\nStatus: " + this.status
                + "\nRole: " + this.role;
    }

    public Date getUpdated() {
        return null;
    }
}
