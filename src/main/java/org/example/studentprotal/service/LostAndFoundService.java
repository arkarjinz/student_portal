package org.example.studentprotal.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.studentprotal.dao.ItemDao;
import org.example.studentprotal.dao.StudentDao;
import org.example.studentprotal.dto.LostAndFoundITemDto;
import org.example.studentprotal.entity.Item;
import org.example.studentprotal.entity.Student;
import org.example.studentprotal.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class LostAndFoundService {
    @Autowired
    private    ItemDao itemDao;
    @Autowired
    private  StudentDao studentDao;

    public List<Item> getAllLostAndFoundItems(){
        List<Item> items =itemDao.findAll();
        return items;
    }
    @Transactional
    public String createLostAndFoundItem(LostAndFoundITemDto lostAndFoundITemDto, int studentId) {
        if(studentDao.existsById(studentId)){
            Student student = studentDao.findById(studentId).get();
            Item item = new Item();
            item.setTitle(lostAndFoundITemDto.getTitle());
            item.setDescription(lostAndFoundITemDto.getDescription());
            item.setImageUrl(lostAndFoundITemDto.getImageUrl());
            item.setFound(false);
            item.addStudent(student);
            itemDao.save(item);
            return "Item created";
        }
        throw new EntityNotFoundException(studentId + " not found!");
    }


}
