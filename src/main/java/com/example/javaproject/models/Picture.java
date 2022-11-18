package com.example.javaproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Picture {
    private Integer id;
    private String name;
    private Author author;
    private Category category;
    private Integer year;
    private String info;
    private byte[] pictureBytes;
}
