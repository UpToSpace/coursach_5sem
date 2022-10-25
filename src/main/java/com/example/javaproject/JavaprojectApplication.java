package com.example.javaproject;

import com.example.javaproject.models.Role;
import com.example.javaproject.models.User;
import com.example.javaproject.services.UserService;
import com.sun.xml.bind.v2.util.DataSourceSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.ArrayList;

@SpringBootApplication
public class JavaprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaprojectApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    CommandLineRunner run(UserService userService) {
//        return args -> {
//            userService.saveUser(new User("Valerie143@mail.ru", "admin1", "1111", new Role(1, "admin")));
//            System.out.println(userService.getAllUsers().stream().findAny());
//        };
//    }
}
