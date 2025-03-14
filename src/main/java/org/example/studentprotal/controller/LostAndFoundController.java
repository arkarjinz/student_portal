package org.example.studentprotal.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.studentprotal.dao.ItemDao;
import org.example.studentprotal.dto.LostAndFoundITemDto;
import org.example.studentprotal.service.LostAndFoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/student-portal/lost-and-found")
public class LostAndFoundController {

    @Autowired
    private LostAndFoundService lostAndFoundService;
    @Autowired
    private ItemDao itemDao;

    // Endpoint to create a lost and found item using multipart/form-data
    @PostMapping("/{id}")
    public ResponseEntity<String> createLostAndFoundItem(
            @PathVariable("id") int studentId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("isFound") boolean isFound,
            @RequestParam("image") MultipartFile imageFile) {
        try {
            lostAndFoundService.createLostAndFoundItem(studentId, title, description, isFound, imageFile);
            return ResponseEntity.status(HttpStatus.CREATED).body("Item created");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing image");
        }
    }

    // Return DTO including Base64 encoded image for display if needed
    record LostAndFoundITemDtoResponse(int id, String title, String description,
                                       String imageBase64, boolean found, String studentName) {
    }

    @GetMapping
    public List<LostAndFoundITemDto> getAllLostAndFoundItems() {
        return itemDao.getAllLostAndFoundItems();
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<String> toggleLostAndFoundStatus(
            @PathVariable("id") int itemId,
            @RequestParam("isFound") boolean isFound) {
        try {
            lostAndFoundService.toggleLostAndFoundStatus(itemId, isFound);
            return ResponseEntity.ok("Item status updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
