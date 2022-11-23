package com.example.javaproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Collection {
    private Integer id;
    private String name;
    private String email;
    private List<Picture> pictures;
}
