package com.example.javaproject.repository;

import com.example.javaproject.models.*;
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
public class PictureRepository {
    Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private String DBURL;
    private String DBUser;
    private String DBPassword;
    private Connection connection;

    PictureRepository() {
        DBURL = "jdbc:oracle:thin:@//192.168.56.101:1521/orcl";
        DBUser = "sys as sysdba";
        DBPassword = "Vv1542139";
        try {
            connection = DriverManager.getConnection(DBURL, DBUser, DBPassword);
        } catch (SQLException e) {
            logger.error("SQLError PictureRepository ctor: " + e.getMessage());
        }
    }

    public void addAuthor(String name, String info) {
        try {
            CallableStatement cs = connection.prepareCall("{call add_author(?, ?)}");
            cs.setString(1, name);
            cs.setString(2, info);
            cs.executeQuery();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }


    public void addCategory(String name, String info) {
        try {
            CallableStatement cs = connection.prepareCall("{call add_category(?, ?)}");
            cs.setString(1, name);
            cs.setString(2, info);
            cs.executeQuery();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public void addPicture(String name, String authorName, String categoryName, Integer year, String info) {
        try {
            CallableStatement cs = connection.prepareCall("{call add_picture(?, ?, ?, ?, ?)}");
            cs.setString(1, name);
            cs.setString(2, authorName);
            cs.setString(3, categoryName);
            cs.setInt(4, year);
            cs.setString(5, info);
            cs.executeQuery();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public List<Picture> getAllPictures() {
        List<Picture> pictures = new ArrayList<>();
        String query = "select * from picture_view";
        try(Statement statement = connection.createStatement())
        {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                pictures.add(new Picture(0, rs.getString(1), new Author(0, rs.getString(3), null),
                        new Category(0, rs.getString(4), null), 0, rs.getString(2)));
            }
        } catch (SQLException e) {
            logger.error("SQL error picture_view: " + e.getMessage());
        }

        return pictures;
    }
}
