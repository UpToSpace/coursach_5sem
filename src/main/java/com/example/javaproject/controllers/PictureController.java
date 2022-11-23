package com.example.javaproject.controllers;

import com.example.javaproject.dto.AllPicturesResponse;
import com.example.javaproject.forms.AddAuthorForm;
import com.example.javaproject.forms.AddCategoryForm;
import com.example.javaproject.forms.AddPictureForm;
import com.example.javaproject.jwt.JWTProvider;
import com.example.javaproject.models.Author;
import com.example.javaproject.models.Category;
import com.example.javaproject.models.Collection;
import com.example.javaproject.models.Picture;
import com.example.javaproject.services.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class PictureController {

    private PictureService pictureService;
    private JWTProvider jwtProvider;
    Logger logger = LoggerFactory.getLogger(PictureController.class);

    @Autowired
    public PictureController(PictureService pictureService, JWTProvider jwtProvider) {
        this.pictureService = pictureService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/allpictures/search")
    public ResponseEntity<List<Picture>> findPictures(@RequestBody String name) {
        return ResponseEntity.ok().body(pictureService.findPictures(name));
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
                addPictureForm.getCategoryName(), addPictureForm.getYear(), addPictureForm.getInfo(), addPictureForm.getPicturePath());
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @DeleteMapping("/admin/pictures/delete/{id}")
    public ResponseEntity deletePicture(@PathVariable(value = "id") Integer id) {
        pictureService.deletePicture(id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @GetMapping("/allpictures") // TODO user cant see delete button
    public ResponseEntity<List<Picture>> allPicturesPage(@RequestHeader(name = "Authorization") String token) {
//        if (jwtProvider.getRoleFromToken(token) == "admin") {
//            return ResponseEntity.ok().body(new AllPicturesResponse(pictureService.getAllPictures(), true));
//        }
//        return ResponseEntity.ok().body(new AllPicturesResponse(pictureService.getAllPictures(), false));
        return ResponseEntity.ok().body(pictureService.getAllPictures());
    }

    @GetMapping("/allauthors") // TODO user cant see delete button
    public ResponseEntity<List<Author>> allAuthorsPage(@RequestHeader(name = "Authorization") String token) {
        return ResponseEntity.ok().body(pictureService.getAllAuthors());
    }

    @GetMapping("/allcategories") // TODO user cant see delete button
    public ResponseEntity<List<Category>> allCategoriesPage(@RequestHeader(name = "Authorization") String token) {
        return ResponseEntity.ok().body(pictureService.getAllCategories());
    }

    @GetMapping("/user/collections")
    public ResponseEntity<List<Collection>> allCollectionsPage(@RequestHeader(name = "Authorization") String token) {
        String email = jwtProvider.getEmailFromHeader(token);
        return ResponseEntity.ok().body(pictureService.getAllUserCollections(email));
    }

    @PostMapping("/user/addcollection")
    public ResponseEntity addCollection(@RequestHeader(name = "Authorization") String token, @RequestBody String name) {
        String email = jwtProvider.getEmailFromHeader(token);
        pictureService.addCollection(name, email);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @PostMapping("/user/addtocollection/{id}")
    public ResponseEntity addToCollection(@PathVariable(value = "id") int pictureId, @RequestBody String collectionName, @RequestHeader(name = "Authorization") String header) {
        String email = jwtProvider.getEmailFromHeader(header);
        pictureService.addPictureToCollection(pictureId, email, collectionName);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @DeleteMapping("/user/collections/deletepicture/{pictureId}")
    public ResponseEntity deletePictureFromCollection(@PathVariable(value = "pictureId") Integer pictureId, @RequestBody int collectionId) {
        pictureService.deletePictureFromCollection(pictureId, collectionId);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
