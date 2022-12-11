package com.example.javaproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

//    @NotEmpty(message = "Fill field username, please")
//    @Size(min = 3, max = 20, message = "Username should be 3-20 characters long")
//    @Email(message = "")
    private String email;

//    @NotEmpty(message = "Fill field username, please")
//    @Size(min = 3, max = 20, message = "Username should be 3-20 characters long")
    private String username;
//
//    @NotEmpty(message = "Fill field username, please")
//    @Size(min = 3, max = 20, message = "Username should be 3-20 characters long")
    private String password;

    private Role role;

    @Override
    public String toString() {
        return "--User-- \nUsername: "
                + this.username
                + "\nEmail: " + this.email
                + "\nPassword: " + this.password
                //+ "\nStatus: " + this.status
                + "\nRole: " + this.role;
    }
}
