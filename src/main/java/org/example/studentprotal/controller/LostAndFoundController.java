package org.example.studentprotal.controller;


import lombok.RequiredArgsConstructor;
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
    private  LostAndFoundService lostAndFoundService;

    //http://localhost:8080/api/student-portal/lost-and-found/5
    record LostAndFoundRequest(String title,
                               String description,
                               String image,
                               boolean isFound){}

    @PostMapping("/{id}")
    public ResponseEntity<String>
    createLostAndFoundItem(@RequestBody LostAndFoundRequest lostAndFoundRequest, @PathVariable("id") int studentId) {
        LostAndFoundITemDto lostAndFoundITemDto = new LostAndFoundITemDto();
        lostAndFoundITemDto.setFound(false);
        lostAndFoundITemDto.setImageUrl(lostAndFoundRequest.image());
        lostAndFoundITemDto.setTitle(lostAndFoundRequest.title());
        lostAndFoundITemDto.setDescription(lostAndFoundRequest.description());
        String responseString=lostAndFoundService.createLostAndFoundItem(lostAndFoundITemDto, studentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseString);
    }


    record LostAndFoundITemDtoResponse(int id, String title, String description,
                               String image,  boolean found, String studentName){}


    @GetMapping
    public List<LostAndFoundITemDtoResponse> getAllLostAndFoundItems(){
        return lostAndFoundService.getAllLostAndFoundItems()
                .stream()
                .map(i -> new LostAndFoundITemDtoResponse(i.getId(),
                        i.getTitle(),
                        i.getDescription(),
                        i.getImageUrl(),
                       i.isFound(),
                       i.getStudent().getName()
                       )).toList();
    }
}
