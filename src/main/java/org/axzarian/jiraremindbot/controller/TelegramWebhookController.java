package org.axzarian.jiraremindbot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axzarian.jiraremindbot.schedule.DailyNotifier;
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

    private final DailyNotifier dailyNotifier;

    @PostMapping
    public ResponseEntity<String> onUpdateReceived(@RequestBody Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            final var text   = update.getMessage().getText();

            if ("/start".equals(text)) {
                log.info("Incoming update: {}", update.getMessage().getChatId());
            }

            if ("/reminder".equals(text)) {
                dailyNotifier.sendDailyNotification();
            }
        }

        return ResponseEntity.ok("OK");
    }
}
