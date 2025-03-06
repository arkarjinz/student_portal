package org.example.studentprotal.controller;

import lombok.RequiredArgsConstructor;
import org.example.studentprotal.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.OffsetDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student-portal/post")
public class PostController {
    private final PostService postService;

    // Updated record to use OffsetDateTime for createdAt
    record PostDto(String postOwner, String content, String title, OffsetDateTime createdAt) {}

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostDto postDto) {
        String responseString = postService.createPost(
                postDto.postOwner(),
                postDto.content(),
                postDto.title(),
                postDto.createdAt()
        );
        return ResponseEntity.ok(responseString);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Integer id, @RequestParam String postOwner) {
        String response = postService.deletePost(id, postOwner);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Integer id,
                                             @RequestParam String postOwner,
                                             @RequestBody PostDto postDto) {
        String response = postService.updatePost(id, postOwner, postDto.content());
        return ResponseEntity.ok(response);
    }
}
