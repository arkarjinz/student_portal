package org.example.studentprotal.dao;

import org.example.studentprotal.dto.PostDto;
import org.example.studentprotal.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PostDao extends JpaRepository<Post, Integer> {
    @Query("""
select new org.example.studentprotal.dto.PostDto( p.id,
                                                   p.title,
                                                   p.content,
                                                   p.createdAt,
                                                   p.student.username,
                                                   p.student.profileImage,
                                                   p.student.roseCount,
                                                   p.likeCount )
from Post p order by p.createdAt desc 
""")
    List<PostDto> findAllPostDto();
}
