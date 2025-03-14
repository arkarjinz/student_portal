package org.example.studentprotal.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.studentprotal.dao.PostDao;
import org.example.studentprotal.dao.PostLikeRepository;
import org.example.studentprotal.dao.StudentDao;
import org.example.studentprotal.entity.Post;
import org.example.studentprotal.entity.PostLike;
import org.example.studentprotal.entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final StudentDao studentDao;
    private final PostDao postDao;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public String createPost(String postOwner, String content, String title, OffsetDateTime createdAt) {
        Optional<Student> studentOptional = studentDao.findByUsername(postOwner);
        if(studentOptional.isPresent()){
            Student student = studentOptional.get();
            Post post = new Post(title, content, createdAt);
            student.addPost(post);
            studentDao.saveAndFlush(student);
        } else {
            throw new EntityNotFoundException("Student not found");
        }
        return "Successfully created.";
    }

    @Transactional
    public String deletePost(Integer postId, String postOwner) {
        Optional<Post> postOptional = postDao.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            if (post.getStudent().getUsername().equals(postOwner)) {
                // Deleting the post will cascade remove its likes because of orphanRemoval on the Post.likes mapping
                Student student = post.getStudent();
                student.getPosts().remove(post);
                studentDao.save(student);
                return "Successfully deleted.";
            } else {
                throw new RuntimeException("You are not authorized to delete this post.");
            }
        } else {
            throw new EntityNotFoundException("Post not found.");
        }
    }

    @Transactional
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

    // Like a post (only if not already liked)
    @Transactional
    public String likePost(Integer postId, String username) {
        Optional<Student> studentOptional = studentDao.findByUsername(username);
        if (!studentOptional.isPresent()) {
            throw new EntityNotFoundException("Student not found");
        }
        Student student = studentOptional.get();

        Optional<Post> postOptional = postDao.findById(postId);
        if (!postOptional.isPresent()) {
            throw new EntityNotFoundException("Post not found");
        }
        Post post = postOptional.get();

        Optional<PostLike> existingLike = postLikeRepository.findByPostIdAndStudentId(postId, student.getId());
        if (existingLike.isPresent()) {
            return "Already liked";
        }

        PostLike like = new PostLike();
        like.setPost(post);
        like.setStudent(student);
        postLikeRepository.save(like);

        post.setLikeCount(post.getLikeCount() + 1);
        postDao.save(post);
        return "Post liked.";
    }

    // Unlike a post
    @Transactional
    public String unlikePost(Integer postId, String username) {
        Optional<Student> studentOptional = studentDao.findByUsername(username);
        if (!studentOptional.isPresent()) {
            throw new EntityNotFoundException("Student not found");
        }
        Student student = studentOptional.get();

        Optional<Post> postOptional = postDao.findById(postId);
        if (!postOptional.isPresent()) {
            throw new EntityNotFoundException("Post not found");
        }
        Post post = postOptional.get();

        Optional<PostLike> existingLike = postLikeRepository.findByPostIdAndStudentId(postId, student.getId());
        if (!existingLike.isPresent()) {
            return "Post not liked yet";
        }

        postLikeRepository.delete(existingLike.get());
        post.setLikeCount(Math.max(post.getLikeCount() - 1, 0));
        postDao.save(post);
        return "Post unliked.";
    }

    // Get IDs of posts liked by a user
    @Transactional(readOnly = true)
    public Set<Integer> getLikedPostIds(String username) {
        Optional<Student> studentOptional = studentDao.findByUsername(username);
        if (!studentOptional.isPresent()) {
            throw new EntityNotFoundException("Student not found");
        }
        Student student = studentOptional.get();
        List<PostLike> likes = postLikeRepository.findByStudentId(student.getId());
        return likes.stream().map(like -> like.getPost().getId()).collect(Collectors.toSet());
    }
}
