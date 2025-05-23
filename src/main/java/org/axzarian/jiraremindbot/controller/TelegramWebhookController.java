package org.axzarian.jiraremindbot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axzarian.jiraremindbot.sender.TelegramSender;
import org.axzarian.jiraremindbot.service.ReminderStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;


@Slf4j
@RestController
@RequestMapping("/telegram")
@RequiredArgsConstructor
public class TelegramWebhookController {

    private final TelegramSender telegramSender;
    private final ReminderStorageService reminderStorageService;

    @PostMapping
    public ResponseEntity<String> onUpdateReceived(@RequestBody Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            final var text   = update.getMessage().getText();

            if ("/scan biological species".equals(text)) {
                log.info("Incoming update: {}", update.getMessage().getChatId());
            }

            if ("/test".equals(text)) {
                final var chatId = update.getMessage().getChatId().toString();
                final var message = reminderStorageService.getMessage();
                final var image = reminderStorageService.getImage();

                telegramSender.sendPhotoFromFile(chatId, image, message);

            }
        }

        return ResponseEntity.ok("OK");
    }
}
