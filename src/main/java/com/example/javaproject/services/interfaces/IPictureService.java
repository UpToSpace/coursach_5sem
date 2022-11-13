package com.example.javaproject.services.interfaces;

import com.example.javaproject.models.Picture;
import com.example.javaproject.models.User;

import java.util.List;

public interface IPictureService {
    Picture getPicture(Integer id);
    List<Picture> getAllPictures();
    void addAuthor(String name, String info);
    void addCategory(String name, String info);
    void addPicture(String name, String authorName, String categoryName, Integer year, String info);
}
