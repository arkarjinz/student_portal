package org.example.studentprotal.dao;

import org.example.studentprotal.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
    Optional<PostLike> findByPostIdAndStudentId(Integer postId, Integer studentId);
    List<PostLike> findByStudentId(Integer studentId);
}
