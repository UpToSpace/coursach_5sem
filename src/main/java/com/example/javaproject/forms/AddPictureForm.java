package com.example.javaproject.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddPictureForm {
    private String name;
    private String authorName;
    private String categoryName;
    private Integer year;
    private String info;
}
