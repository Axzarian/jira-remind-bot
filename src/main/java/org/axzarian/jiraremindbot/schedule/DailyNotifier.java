package org.axzarian.jiraremindbot.schedule;

import lombok.RequiredArgsConstructor;
import org.axzarian.jiraremindbot.sender.TelegramSender;
import org.axzarian.jiraremindbot.service.ReminderStorageService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyNotifier {

    private final TelegramSender         telegramSender;
    private final ReminderStorageService reminderStorageService;

//    private static final String TEST_GROUP_CHAT_ID = "-1002611733910";
    private static final String JIRA_REMIND_BOT_CHAT_ID = "-676037852";


    @Scheduled(cron = "0 50 09 * * TUE-FRI", zone = "Asia/Almaty")
    public void sendDailyNotification() {

        final var message = reminderStorageService.getMessage();
        final var image   = reminderStorageService.getImage();

        telegramSender.sendPhotoFromFile(JIRA_REMIND_BOT_CHAT_ID, image, message);
    }

}
