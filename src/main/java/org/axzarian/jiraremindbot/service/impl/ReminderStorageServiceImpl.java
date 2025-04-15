package org.axzarian.jiraremindbot.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.axzarian.jiraremindbot.service.ReminderStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReminderStorageServiceImpl implements ReminderStorageService {

    private static       String MESSAGE    = """
                                             *😱 ЕМААААААА\\!*
                                             
                                             Через *5 минут* — дейлик\\!
                                             
                                             Где\\: *Конкорд 301*
                                             
                                             Подвинь тикеты, обнови статусы, не зли *уважаемых людей* 🧑‍💼👀
                                             
                                             [🔗 Jira](https://jira-obl.hq.bc/secure/RapidBoard.jspa?rapidView=13&projectKey=KSSN)
                                             """;

    private static final Path   IMAGE_PATH = Paths.get("/home/ubuntu/jira-bot/img/image1.png");


    @Override
    public void save(String text, MultipartFile image) {
        MESSAGE = text;

        try {
            image.transferTo(IMAGE_PATH.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

    @Override
    public File getImage() {
        return IMAGE_PATH.toFile();
    }


}
