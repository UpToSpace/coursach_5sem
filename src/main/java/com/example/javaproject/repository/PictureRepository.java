package com.example.javaproject.repository;

import com.example.javaproject.models.*;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public List<Picture> findPictures(String name) {
        List<Picture> pictures = new ArrayList<>();
        try {
            CallableStatement cs = connection.prepareCall("{? = call search_picture(?)}");
            cs.setString(2, name);
            cs.registerOutParameter(1, Types.REF_CURSOR);
            cs.executeQuery();
            ResultSet rs = cs.getObject(1, ResultSet.class);
            while(rs.next()) {
                Blob blob = rs.getBlob(7);
                byte pictureBytes[] = blob.getBytes(1, (int)blob.length());
                pictures.add(new Picture(rs.getInt(5), rs.getString(1), new Author(0, rs.getString(3), null),
                        new Category(0, rs.getString(4), null), rs.getInt(6), rs.getString(2), pictureBytes));
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return pictures;
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

    public void addPicture(String name, String authorName, String categoryName, Integer year, String info, String picturePath) {
        try {
            CallableStatement cs = connection.prepareCall("{call add_picture(?, ?, ?, ?, ?, ?)}");
            cs.setString(1, name);
            cs.setString(2, authorName);
            cs.setString(3, categoryName);
            cs.setInt(4, year);
            cs.setString(5, info);
            FileInputStream fileInputStream = new FileInputStream(picturePath);
            cs.setBinaryStream(6, fileInputStream, fileInputStream.available());
            cs.executeQuery();
            cs.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCollection(String name, String email) {
        try {
            CallableStatement cs = connection.prepareCall("{call add_collection(?, ?)}");
            cs.setString(1, name);
            cs.setString(2, email);
            cs.executeQuery();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public void addPictureToCollection(int id, String email, String collectionName) {
        try {
            CallableStatement cs = connection.prepareCall("{call add_picture_to_collection(?, ?, ?)}");
            cs.setInt(1, id);
            cs.setString(2, email);
            cs.setString(3, collectionName);
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
                Blob blob = rs.getBlob(7);
                byte pictureBytes[] = blob.getBytes(1, (int)blob.length());
                pictures.add(new Picture(rs.getInt(5), rs.getString(1), new Author(0, rs.getString(3), null),
                        new Category(0, rs.getString(4), null), rs.getInt(6), rs.getString(2), pictureBytes));
            }
        } catch (SQLException e) {
            logger.error("SQL error picture_view: " + e.getMessage());}

        return pictures;
    }

    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        String query = "select * from authors";
        try(Statement statement = connection.createStatement())
        {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                authors.add(new Author(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            logger.error("SQL error authors table: " + e.getMessage());
        }

        return authors;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "select * from CATEGORIES";
        try(Statement statement = connection.createStatement())
        {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                categories.add(new Category(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            logger.error("SQL error categories table: " + e.getMessage());
        }

        return categories;
    }

    public List<Collection> getAllUserCollections(String email) {
        List<Collection> collections = new ArrayList<>();
        try {
            CallableStatement cs = connection.prepareCall("{call list_users_collections(?, ?)}");
            cs.setString(1, email);
            cs.registerOutParameter(2, Types.REF_CURSOR);
            cs.executeQuery();
            ResultSet rs = cs.getObject(2, ResultSet.class);
            int id = -10;
            int prevId = id;
            Collection collection = new Collection();
            List<Picture> pictures = new ArrayList<>();
            while(rs.next()) {
                Picture picture = new Picture(rs.getInt(4), rs.getString(5), new Author(0, rs.getString(6), null),
                        new Category(0, rs.getString(7), null), rs.getInt(8), rs.getString(9), rs.getBlob(10).getBytes(1, (int)rs.getBlob(10).length()));
                pictures.add(picture);
                id = rs.getInt(1);
                if (id != prevId) {
                    collection.setId(id);
                    collection.setName(rs.getString(2));
                    collection.setEmail(rs.getString(3));
                    collection.setPictures(pictures);
                    collections.add(collection);
                    collection = new Collection();
                    pictures = new ArrayList<>();
                }
                prevId = id;
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return collections;
    }

    public void deletePicture(Integer id) {
        try {
            CallableStatement cs = connection.prepareCall("{call delete_picture(?)}");
            cs.setInt(1, id);
            cs.executeQuery();
        } catch (SQLException e) {
            logger.error("SQL error delete picture: " + e.getMessage());;
        }
        logger.info("picture " + id + " deleted successfully");
    }

    public void deletePictureFromCollection(int picture_id, int collection_id) {
        try {
            CallableStatement cs = connection.prepareCall("{call delete_picture_from_collection(?, ?)}");
            cs.setInt(1, picture_id);
            cs.setInt(2, collection_id);
            cs.executeQuery();
        } catch (SQLException e) {
            logger.error("SQL error delete picture: " + e.getMessage());;
        }
        logger.info("picture " + picture_id + " deleted successfully");
    }
}
