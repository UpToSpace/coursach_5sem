package com.example.javaproject;

import com.sun.xml.bind.v2.util.DataSourceSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class JavaprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaprojectApplication.class, args);
    }

}
