package org.axzarian.jiraremindbot.sender;

import java.io.File;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class TelegramSender {

    @Value("${telegram.token}")
    private String botToken;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendMessage(String chatId, String text) {

        final var url = String.format("https://api.telegram.org/bot%s/sendMessage", botToken);

        Map<String, String> payload = Map.of("chat_id", chatId, "text", text);

        restTemplate.postForObject(url, payload, String.class);
    }

    public void sendPhotoFromFile(String chatId, File file, String caption) {
        final var url = String.format("https://api.telegram.org/bot%s/sendPhoto", botToken);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("chat_id", chatId);
        body.add("photo", new FileSystemResource(file));
        body.add("caption", caption);
        body.add("parse_mode", "MarkdownV2");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        restTemplate.postForObject(url, request, String.class);
    }
}
