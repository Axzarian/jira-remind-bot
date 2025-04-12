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

    private static final String GROUP_CHAT_ID = "-4725997680";


    @Scheduled(cron = "0 25 12 * * *", zone = "Asia/Almaty")
    public void sendDailyNotificationScheduled() {
        final var message = reminderStorageService.getMessage();
        telegramSender.sendMarkDown(GROUP_CHAT_ID, message);
    }

    @Scheduled(cron = "0 0 * * * *", zone = "Asia/Almaty")
    public void sendDailyNotification() {

        final var message = reminderStorageService.getMessage();
        final var image   = reminderStorageService.getImage();

        telegramSender.sendPhotoFromFile(GROUP_CHAT_ID, image, message);
    }

}
