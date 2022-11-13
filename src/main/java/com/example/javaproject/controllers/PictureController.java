package com.example.javaproject.controllers;

import com.example.javaproject.forms.AddAuthorForm;
import com.example.javaproject.forms.AddCategoryForm;
import com.example.javaproject.forms.AddPictureForm;
import com.example.javaproject.models.Picture;
import com.example.javaproject.repository.PictureRepository;
import com.example.javaproject.services.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class PictureController {

    private PictureService pictureService;
    Logger logger = LoggerFactory.getLogger(PictureController.class);

    @Autowired
    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @PostMapping("/admin/addauthor")
    public ResponseEntity addAuthor(@RequestBody AddAuthorForm addAuthorForm) {
        pictureService.addAuthor(addAuthorForm.getName(), addAuthorForm.getInfo());
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @PostMapping("/admin/addcategory")
    public ResponseEntity addAuthor(@RequestBody AddCategoryForm addCategoryForm) {
        pictureService.addCategory(addCategoryForm.getName(), addCategoryForm.getInfo());
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @PostMapping("/admin/addpicture")
    public ResponseEntity addPicture(@RequestBody AddPictureForm addPictureForm) {
        pictureService.addPicture(addPictureForm.getName(), addPictureForm.getAuthorName(),
                addPictureForm.getCategoryName(), addPictureForm.getYear(), addPictureForm.getInfo());
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @GetMapping("/pictures")
    public ResponseEntity<List<Picture>> allPicturesPage() {
        return ResponseEntity.ok().body(pictureService.getAllPictures());
    }
}
