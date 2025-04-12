package org.axzarian.jiraremindbot.schedule;

import java.io.File;
import lombok.RequiredArgsConstructor;
import org.axzarian.jiraremindbot.sender.TelegramSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyNotifier {

    private final TelegramSender telegramSender;

    private static final String GROUP_CHAT_ID = "-4725997680";

    private static final String MESSAGE = """
                                          *😱 ЕМААААААА\\!*
                                          
                                          Через *15 минут* — дейлик\\!
                                          
                                          Подвинь тикеты, обнови статусы, не зли *уважаемых людей* 🧑‍💼👀
                                          
                                          🛠️ Jira 👇
                                          
                                          [🔗 Доска](https://google.com)
                                          """;


    @Scheduled(cron = "0 25 12 * * *", zone = "Asia/Almaty")
    public void sendDailyNotificationScheduled() {
        telegramSender.sendMarkDown(GROUP_CHAT_ID, MESSAGE);
    }

    public void sendDailyNotification() {
        final var file = new File("/Users/alexey/Downloads/image1.png");
        telegramSender.sendPhotoFromFile(GROUP_CHAT_ID, file, MESSAGE);
    }

}
