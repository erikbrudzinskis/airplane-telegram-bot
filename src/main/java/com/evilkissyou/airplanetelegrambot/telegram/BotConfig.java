package com.evilkissyou.airplanetelegrambot.telegram;

import com.evilkissyou.airplanetelegrambot.controller.Keyboards;
import com.evilkissyou.airplanetelegrambot.controller.MessageDispatcher;
import com.evilkissyou.airplanetelegrambot.data.AirplaneRepository;
import com.evilkissyou.airplanetelegrambot.data.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BotConfig {

    @Value("#{environment.BOT_TOKEN}")
    private String botToken;

    @Value("#{environment.BOT_NAME}")
    private String botName;

    @Autowired
    AirplaneRepository airplaneRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    Keyboards keyboards;


    @Bean
    public Bot bot() {
        Bot bot = new Bot();
        bot.setBotUsername(botName);
        bot.setBotToken(botToken);
        return bot;
    }

    @Bean
    @Scope("prototype")
    public MessageDispatcher messageDispatcher() {
        return new MessageDispatcher(airplaneRepository, usersRepository, keyboards);
    }
}