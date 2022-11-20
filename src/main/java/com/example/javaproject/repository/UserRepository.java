package com.example.javaproject.repository;

import com.example.javaproject.models.Role;
import com.example.javaproject.models.User;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class UserRepository {
    Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private String DBURL;
    private String DBUser;
    private String DBPassword;
    private Connection connection;

    UserRepository() {
        DBURL = "jdbc:oracle:thin:@//192.168.56.101:1521/orcl";
        DBUser = "sys as sysdba";
        DBPassword = "Vv1542139";
        try {
            connection = DriverManager.getConnection(DBURL, DBUser, DBPassword);
        } catch (SQLException e) {
            logger.error("SQLError UserRepository ctor: " + e.getMessage());
        }
    }

    public User loginUser(String email, String password) {
        User user = null;
        try {
            CallableStatement cs = connection.prepareCall("{call log_in_user(?, ?, ?, ?, ?)}");
            cs.setString(1, email);
            cs.setString(2, password);
            cs.registerOutParameter(3, OracleTypes.VARCHAR);
            cs.registerOutParameter(4, OracleTypes.NUMBER);
            cs.registerOutParameter(5, OracleTypes.VARCHAR);
            cs.executeQuery();
            user = new User(email, cs.getString(3), password, new Role(cs.getInt(4), cs.getString(5)));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "select * from user_userrole_view";
        try(Statement statement = connection.createStatement())
        {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                users.add(new User(rs.getString(1), rs.getString(2), null, new Role(0, rs.getString(3))));
            }
        } catch (SQLException e) {
            logger.error("SQL error user_userrole_view: " + e.getMessage());
        }

        return users;
    }

    public void registerUser(String email, String username, String password) {
        try {
            CallableStatement cs = connection.prepareCall("{call register_user(?, ?, ?)}");
            cs.setString(1, email);
            cs.setString(2, username);
            cs.setString(3, password);
            cs.executeQuery();
        } catch (SQLException e) {
            logger.error("SQL error register user: " + e.getMessage());;
        }
        logger.info("user " + email + " registered successfully");
    }

    public User getUser(String email) {
        String query = "select * from full_user_userrole_view where upper(email) = upper('" + email + "')";
        User user = null;
        try
        {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                user = new User(rs.getString(1), rs.getString(2), rs.getString(3), new Role(0, rs.getString(4)));
            }
        } catch (SQLException e) {
            logger.error("SQL error full_user_userrole_view: " + e.getMessage());
        }
        return user;
    }

    public void updateUser(String email, String username, String password) {
        try {
            CallableStatement cs = connection.prepareCall("{call update_user(?, ?, ?)}");
            cs.setString(1, email);
            cs.setString(2, username);
            cs.setString(3, password);
            cs.executeQuery();
        } catch (SQLException e) {
            logger.error("SQL error update user: " + e.getMessage());;
        }
        logger.info("user " + email + " updated successfully");
    }

    public void deleteUser(String email) {
        try {
            CallableStatement cs = connection.prepareCall("{call delete_user(?)}");
            cs.setString(1, email);
            cs.executeQuery();
        } catch (SQLException e) {
            logger.error("SQL error delete user: " + e.getMessage());;
        }
        logger.info("user " + email + " deleted successfully");
    }


}
