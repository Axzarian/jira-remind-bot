package org.axzarian.jiraremindbot.controller;

import lombok.RequiredArgsConstructor;
import org.axzarian.jiraremindbot.schedule.DailyNotifier;
import org.axzarian.jiraremindbot.sender.TelegramSender;
import org.axzarian.jiraremindbot.stopwatch.Stopwatch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequestMapping("/telegram")
@RequiredArgsConstructor
public class TelegramWebhookController {

    private final TelegramSender telegramSender;
    private final Stopwatch      stopwatch;
    private final DailyNotifier dailyNotifier;

    @PostMapping
    public ResponseEntity<String> onUpdateReceived(@RequestBody Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            final var text   = update.getMessage().getText();
            final var chatId = update.getMessage().getChatId();

            if ("/start".equals(text)) {
//                telegramSender.sendWithButtons(chatId.toString(), formatUptime());
                dailyNotifier.sendDailyNotification();
            } else {
                telegramSender.sendMessage(chatId.toString(), "Неизвестная команда.");
            }
        }

        if (update.hasCallbackQuery()) {
            final var data      = update.getCallbackQuery().getData();
            final var chatId    = update.getCallbackQuery().getMessage().getChatId();
            final var messageId = update.getCallbackQuery().getMessage().getMessageId();

            if ("refresh".equals(data)) {
                telegramSender.editMessage(chatId.toString(), messageId, formatUptime());
            } else if ("reset".equals(data)) {
//                stopwatch.reset();
                telegramSender.sendWithButtons(
                    chatId.toString(),
                    "Я передумал, ты не можешь сбросить таймер.\n\n" + formatUptime()
                );
            }
        }

        return ResponseEntity.ok("OK");
    }

    private String formatUptime() {
        long totalSeconds = stopwatch.getTime();

        long days    = totalSeconds / (60 * 60 * 24);
        long hours   = (totalSeconds / (60 * 60)) % 24;
        long minutes = (totalSeconds / 60) % 60;
        long seconds = totalSeconds % 60;

        return "Days: %d, hours: %d, min: %d, sec: %d".formatted(days, hours, minutes, seconds);
    }
}
