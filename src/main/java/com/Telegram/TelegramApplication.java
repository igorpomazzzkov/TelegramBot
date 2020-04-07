package com.Telegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;


@SpringBootApplication
public class TelegramApplication {
	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(TelegramApplication.class);
	}
}
