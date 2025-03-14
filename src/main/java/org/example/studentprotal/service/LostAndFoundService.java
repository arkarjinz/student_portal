package org.example.studentprotal.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.studentprotal.dao.ItemDao;
import org.example.studentprotal.dao.StudentDao;
import org.example.studentprotal.entity.Item;
import org.example.studentprotal.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class LostAndFoundService {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private StudentDao studentDao;

    public List<Item> getAllLostAndFoundItems() {
        return itemDao.findAll();
    }

    @Transactional
    public void createLostAndFoundItem(int studentId, String title, String description, boolean isFound,
                                       MultipartFile imageFile) throws IOException {
        Optional<Student> optionalStudent = studentDao.findById(studentId);
        if (optionalStudent.isEmpty()) {
            throw new EntityNotFoundException(studentId + " not found!");
        }
        Student student = optionalStudent.get();
        Item item = new Item();
        item.setTitle(title);
        item.setDescription(description);
        item.setFound(isFound);
        item.addStudent(student);
        // Store the uploaded file bytes
        item.setImage(imageFile.getBytes());
        itemDao.save(item);
    }

    // LostAndFoundService.java
    @Transactional
    public void toggleLostAndFoundStatus(int itemId, boolean isFound) {
        Item item = itemDao.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item with id " + itemId + " not found"));
        item.setFound(isFound);
        itemDao.save(item);
    }
}
