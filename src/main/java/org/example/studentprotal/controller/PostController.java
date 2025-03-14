package org.example.studentprotal.controller;

import lombok.RequiredArgsConstructor;
import org.example.studentprotal.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.OffsetDateTime;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/api/student-portal/post")
public class PostController {
    private final PostService postService;

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
    public ResponseEntity<String> deletePost(@PathVariable Integer id,
                                             @RequestParam("postOwner") String postOwner) {
        String response = postService.deletePost(id, postOwner);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Integer id,
                                             @RequestParam("postOwner") String postOwner,
                                             @RequestBody PostDto postDto) {
        String response = postService.updatePost(id, postOwner, postDto.content());
        return ResponseEntity.ok(response);
    }

    // Endpoint to like a post
    @PostMapping("/{id}/like")
    public ResponseEntity<String> likePost(@PathVariable Integer id,
                                           @RequestParam String username) {
        String response = postService.likePost(id, username);
        return ResponseEntity.ok(response);
    }

    // Endpoint to unlike a post
    @PostMapping("/{id}/unlike")
    public ResponseEntity<String> unlikePost(@PathVariable Integer id,
                                             @RequestParam String username) {
        String response = postService.unlikePost(id, username);
        return ResponseEntity.ok(response);
    }

    // Endpoint to fetch liked post IDs for a given user
    @GetMapping("/liked")
    public ResponseEntity<Set<Integer>> getLikedPosts(@RequestParam String username) {
        Set<Integer> likedIds = postService.getLikedPostIds(username);
        return ResponseEntity.ok(likedIds);
    }
}
