package com.evilkissyou.airplanetelegrambot.telegram;

import com.evilkissyou.airplanetelegrambot.controller.MessageDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class Bot extends TelegramLongPollingBot {
    private String botToken;
    private String botName;

    @Autowired
    private MessageDispatcher messageDispatcher;

    @Override
    public void onUpdateReceived(Update update) {
        Message message = null;
        String data = null;
        boolean hasCallback = false,
                hasRegularMessage = false;

        // Check if the update was made from Inline Keyboard (callback) or by regular message
        // After that, get message and data
        if (update.hasCallbackQuery()) {
            hasCallback = true;
            CallbackQuery callbackQuery = update.getCallbackQuery();
            message = callbackQuery.getMessage();
            data = callbackQuery.getData();
        } else if (update.hasMessage()) {
            hasRegularMessage = true;
            message = update.getMessage();
            data = message.getText();
        }

        // Decide which method from messageDispatcher will be used depending on update type
        if (hasCallback || hasRegularMessage) {
            String chatId = message.getChatId().toString();
            if (hasCallback) {
                try {
                    execute(messageDispatcher.getResponseByCallback(chatId, data));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    execute(messageDispatcher.getResponseByRegularMessage(chatId, data));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public void setBotUsername(String botName) {
        this.botName = botName;
    }
}