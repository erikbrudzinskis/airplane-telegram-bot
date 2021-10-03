package com.evilkissyou.airplanetelegrambot;

import com.evilkissyou.airplanetelegrambot.controller.MessageDispatcher;
import com.evilkissyou.airplanetelegrambot.data.AirplaneRepository;
import com.evilkissyou.airplanetelegrambot.telegram.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class AirplaneTelegramBotApplication {

	@Autowired
	AirplaneRepository airplaneRepository;

	public static void main(String[] args) {
		ApiContextInitializer.init();
		TelegramBotsApi botsApi = new TelegramBotsApi();
		try {
			botsApi.registerBot(new Bot());
		} catch (TelegramApiException ignored) {
		}
		SpringApplication.run(AirplaneTelegramBotApplication.class, args);
	}

	@PostConstruct
	public void init() {
		MessageDispatcher.maxAirplaneNumber = airplaneRepository.findAll().size();
	}
}
