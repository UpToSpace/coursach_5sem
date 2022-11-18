package com.example.javaproject.services;

import com.example.javaproject.models.Author;
import com.example.javaproject.models.Category;
import com.example.javaproject.models.Picture;
import com.example.javaproject.repository.PictureRepository;
import com.example.javaproject.services.interfaces.IPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureService implements IPictureService {

    private final PictureRepository pictureRepository;

    @Autowired
    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Picture getPicture(Integer id) {
        return null;
    }

    @Override
    public List<Picture> getAllPictures() {
        return pictureRepository.getAllPictures();
    }

    @Override
    public List<Author> getAllAuthors() {
        return pictureRepository.getAllAuthors();
    }

    @Override
    public List<Category> getAllCategories() {
        return pictureRepository.getAllCategories();
    }

    @Override
    public void addAuthor(String name, String info) {
        pictureRepository.addAuthor(name, info);
    }

    @Override
    public void addCategory(String name, String info) {
        pictureRepository.addCategory(name, info);
    }

    @Override
    public void addPicture(String name, String authorName, String categoryName, Integer year, String info, String picturePath) {
        pictureRepository.addPicture(name, authorName, categoryName, year, info, picturePath);
    }

    @Override
    public void deletePicture(Integer id) {
        pictureRepository.deletePicture(id);
    }
}
