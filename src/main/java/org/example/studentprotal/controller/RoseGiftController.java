package org.example.studentprotal.controller;

import org.example.studentprotal.dto.StudentDto;
import org.example.studentprotal.service.RoseGiftService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rose-gift")
public class RoseGiftController {
    private final RoseGiftService roseGiftService;

    public RoseGiftController(RoseGiftService roseGiftService) {
        this.roseGiftService = roseGiftService;
    }
    @PostMapping("/send-rose/{fromUsername}/{toUsername}/{roses}")
    public String sendRoseToStudent(@PathVariable("fromUsername") String fromUsername,
                                    @PathVariable("toUsername") String toUsername,
                                    @PathVariable("roses") int roses) {
        System.out.println(fromUsername + " sends " + roses + " roses to " + toUsername);
        return roseGiftService.sendRose(fromUsername, toUsername, roses);
    }

    @PostMapping("/buy-rose/{username}/{roses}")
    public StudentDto buyRoseForGift(@PathVariable("username") String username,
                                     @PathVariable("roses") int roses) {
        return roseGiftService.buyRose(username, roses);
    }


}
