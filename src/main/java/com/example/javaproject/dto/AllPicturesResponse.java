package com.example.javaproject.dto;

import com.example.javaproject.models.Picture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllPicturesResponse {
    private List<Picture> pictures;
    private boolean deleteButtonVisible;
}
