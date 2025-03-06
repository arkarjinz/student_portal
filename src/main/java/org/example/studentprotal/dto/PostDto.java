package org.example.studentprotal.dto;

import java.time.OffsetDateTime;

public class PostDto {
    private Integer id;
    private String title;
    private String content;
    private OffsetDateTime createdAt;
    private String postOwner;
    private String profileImage;
    private int roseCount;

    public PostDto() {
    }

    public PostDto(Integer id, String title, String content, OffsetDateTime createdAt, String postOwner, String profileImage, int roseCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.postOwner = postOwner;
        this.profileImage = profileImage;
        this.roseCount = roseCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPostOwner() {
        return postOwner;
    }

    public void setPostOwner(String postOwner) {
        this.postOwner = postOwner;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getRoseCount() {
        return roseCount;
    }

    public void setRoseCount(int roseCount) {
        this.roseCount = roseCount;
    }
}
