package org.example.studentprotal.dao;

import org.example.studentprotal.dto.LostAndFoundITemDto;
import org.example.studentprotal.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//private Integer id;
//private String title;
//private String description;
//private String imageUrl;
//private boolean isFound;
//private String studentName;


public interface ItemDao extends JpaRepository<Item, Integer> {
    @Query("""
select new org.example.studentprotal.dto.LostAndFoundITemDto(item.id,item.title,item.description,item.imageUrl,item.isFound,s.username) from Item item join item.student s
""")
    List<LostAndFoundITemDto> getAllLostAndFoundItems();


}
