package org.example.studentprotal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Base64;

@Getter
@Setter
@NoArgsConstructor
public class LostAndFoundITemDto {
    private Integer id;
    private String title;
    private String description;
    // Renamed field to hold Base64 encoded image data
    private String imageBase64;
    private boolean isFound;
    private String studentName;

    // This constructor is used in the JPQL query below.
    // It converts the byte[] image into a Base64 String.
    public LostAndFoundITemDto(Integer id, String title, String description, byte[] image, boolean isFound, String studentName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageBase64 = image != null ? Base64.getEncoder().encodeToString(image) : null;
        this.isFound = isFound;
        this.studentName = studentName;
    }
}
