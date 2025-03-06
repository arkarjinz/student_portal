package org.example.studentprotal.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.studentprotal.dao.PostDao;
import org.example.studentprotal.dao.StudentDao;
import org.example.studentprotal.entity.Post;
import org.example.studentprotal.entity.Student;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final StudentDao studentDao;
    private final PostDao postDao;

    public String createPost(String postOwner, String content, String title, OffsetDateTime createdAt) {
        Optional<Student> studentOptional = studentDao.findByUsername(postOwner);
        if(studentOptional.isPresent()){
            Student student = studentOptional.get();
            Post post = new Post(title, content, createdAt);
            student.addPost(post);
            studentDao.saveAndFlush(student);
        } else {
            throw new EntityNotFoundException("student not found");
        }
        return "successfully created.";
    }

    public String deletePost(Integer postId, String postOwner) {
        Optional<Post> postOptional = postDao.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            if (post.getStudent().getUsername().equals(postOwner)) {
                postDao.delete(post);
                return "Successfully deleted.";
            } else {
                throw new RuntimeException("You are not authorized to delete this post.");
            }
        } else {
            throw new EntityNotFoundException("Post not found.");
        }
    }

    public String updatePost(Integer postId, String postOwner, String newContent) {
        Optional<Post> postOptional = postDao.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            if (post.getStudent().getUsername().equals(postOwner)) {
                post.setContent(newContent);
                postDao.save(post);
                return "Successfully updated.";
            } else {
                throw new RuntimeException("You are not authorized to update this post.");
            }
        } else {
            throw new EntityNotFoundException("Post not found.");
        }
    }

}
