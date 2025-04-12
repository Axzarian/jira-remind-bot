package org.axzarian.jiraremindbot;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JiraRemindBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(JiraRemindBotApplication.class, args);

        ZoneId     zone   = ZoneId.of("Asia/Almaty");
        ZoneOffset offset = zone.getRules().getOffset(Instant.now());

        System.out.println("Zone ID: " + zone);
        System.out.println("Current offset: " + offset);
    }
}
