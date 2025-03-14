package org.example.studentprotal.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class PostDto {
    private Integer id;
    private String title;
    private String content;
    private OffsetDateTime createdAt;
    private String postOwner;
    private String profileImage;
    private int roseCount;
    private int likeCount; // New field

    public PostDto() {}

    public PostDto(Integer id, String title, String content, OffsetDateTime createdAt,
                   String postOwner, String profileImage, int roseCount, int likeCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.postOwner = postOwner;
        this.profileImage = profileImage;
        this.roseCount = roseCount;
        this.likeCount = likeCount;
    }

    // Getters and setters...
    // (Omitted for brevity)
}
