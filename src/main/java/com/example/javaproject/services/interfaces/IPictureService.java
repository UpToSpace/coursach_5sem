package com.example.javaproject.services.interfaces;

import com.example.javaproject.models.*;

import java.util.List;

public interface IPictureService {
    Picture getPicture(Integer id);
    List<Picture> findPictures(String name);
    List<Picture> getAllPictures();
    List<Author> getAllAuthors();
    List<Category> getAllCategories();
    List<Collection> getAllUserCollections(String email);
    void addAuthor(String name, String info);
    void addCategory(String name, String info);
    void addPicture(String name, String authorName, String categoryName, Integer year, String info, String picturePath);
    void addCollection(String name, String email);
    void addPictureToCollection(int id, String email, String collectionName);
    void deletePicture(Integer id);
    void deletePictureFromCollection(int picture_id, int collection_id);
}
