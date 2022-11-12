package com.example.javaproject.services.interfaces;

import com.example.javaproject.models.Picture;
import com.example.javaproject.models.User;

import java.util.List;

public interface IPictureService {
    Picture getPicture(Integer id);
    List<Picture> getAllPictures();
    Picture savePicture(Picture picture);
}
