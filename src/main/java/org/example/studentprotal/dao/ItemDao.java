package org.example.studentprotal.dao;

import org.example.studentprotal.dto.LostAndFoundITemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.example.studentprotal.entity.Item;

import java.util.List;

public interface ItemDao extends JpaRepository<Item, Integer> {

    @Query("select new org.example.studentprotal.dto.LostAndFoundITemDto(item.id, item.title, item.description, item.image, item.isFound, s.username) " +
            "from Item item join item.student s")
    List<LostAndFoundITemDto> getAllLostAndFoundItems();
}
