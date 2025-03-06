package org.example.studentprotal.util;

import org.example.studentprotal.dto.LostAndFoundITemDto;
import org.example.studentprotal.dto.PostDto;
import org.example.studentprotal.dto.StudentDto;
import org.example.studentprotal.entity.Item;
import org.example.studentprotal.entity.Post;
import org.example.studentprotal.entity.Student;
import org.springframework.beans.BeanUtils;

public class EntityUtil {
    public static PostDto toPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setPostOwner(post.getStudent().getName());
        postDto.setProfileImage(post.getStudent().getProfileImage());
        return postDto;
    }
    public static StudentDto studentDto(Student student){
        StudentDto studentDto=new StudentDto();
        BeanUtils.copyProperties(student,studentDto);
        return studentDto;
    }

    public static Post toPost(PostDto postDto) {
        Post post = new Post();
        BeanUtils.copyProperties(postDto, post);
        return post;
    }

    public static Item toItem(LostAndFoundITemDto lostAndFoundITemDto) {
        Item item = new Item();
        BeanUtils.copyProperties(lostAndFoundITemDto, item);
        return item;
    }
}
