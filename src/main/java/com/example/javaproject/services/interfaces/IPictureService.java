package com.example.javaproject.services.interfaces;

import com.example.javaproject.models.Author;
import com.example.javaproject.models.Category;
import com.example.javaproject.models.Picture;
import com.example.javaproject.models.User;

import java.util.List;

public interface IPictureService {
    Picture getPicture(Integer id);
    List<Picture> findPictures(String name);
    List<Picture> getAllPictures();
    List<Author> getAllAuthors();
    List<Category> getAllCategories();
    void addAuthor(String name, String info);
    void addCategory(String name, String info);
    void addPicture(String name, String authorName, String categoryName, Integer year, String info, String picturePath);
    void deletePicture(Integer id);
    void addPictureToCollection(Integer pictureId, String email);
}
