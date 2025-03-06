package org.example.studentprotal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ResponseLostAndFoundItemDto {

    private Integer id;
    private String title;
    private String description;
    private String imageUrl;
    private boolean isFound;
    private String studentName;
    private String base64Image;

    public ResponseLostAndFoundItemDto(Integer id, String title, String description, String imageUrl, boolean isFound, String studentName, String base64Image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isFound = isFound;
        this.studentName = studentName;
        this.base64Image = base64Image;
    }
}
