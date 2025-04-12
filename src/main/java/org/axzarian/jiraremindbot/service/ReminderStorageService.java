package org.axzarian.jiraremindbot.service;

import java.io.File;
import org.springframework.web.multipart.MultipartFile;

public interface ReminderStorageService {

    void save(String text, MultipartFile image);

    String getMessage();

    File getImage();
}
