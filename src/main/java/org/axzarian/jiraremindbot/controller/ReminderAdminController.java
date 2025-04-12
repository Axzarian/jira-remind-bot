package org.axzarian.jiraremindbot.controller;

import lombok.RequiredArgsConstructor;
import org.axzarian.jiraremindbot.service.ReminderStorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ReminderAdminController {

    private final ReminderStorageService reminderStorageService;


    @PostMapping(value = "/reminder", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateNotificationContent(
        @RequestPart("text") String text,
        @RequestPart("image") MultipartFile image
    ) {
        reminderStorageService.save(text, image);
        return ResponseEntity.ok("Reminder updated");
    }
}
