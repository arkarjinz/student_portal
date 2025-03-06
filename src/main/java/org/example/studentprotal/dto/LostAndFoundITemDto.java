package org.example.studentprotal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class LostAndFoundITemDto {
    private Integer id;
    private String title;
    private String description;
    private String imageUrl;
    private boolean isFound;
    private String studentName;


    public LostAndFoundITemDto(Integer id, String title, String description, String imageUrl, boolean isFound, String studentName, String base64Image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isFound = isFound;
        this.studentName = studentName;

    }
}