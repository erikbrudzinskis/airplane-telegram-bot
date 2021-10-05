package com.evilkissyou.airplanetelegrambot.telegram;

import static com.evilkissyou.airplanetelegrambot.constants.Constants.*;
import static com.evilkissyou.airplanetelegrambot.constants.Constants.COMMAND_HELP;

import com.evilkissyou.airplanetelegrambot.controller.MessageDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.GetMyCommands;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private String botToken;
    private String botName;

    @Autowired
    private MessageDispatcher messageDispatcher;

    @Override
    public void onUpdateReceived(Update update) {
        setCommands();

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

        // Get the chat id and register new user and send welcome message, if chat id was not previously registered in db
        String chatId = message.getChatId().toString();

        // Decide which method from messageDispatcher will be used depending on update type
        if (hasCallback || hasRegularMessage) {
            if (hasCallback) {
                try {
                    execute(messageDispatcher.getResponseByCallback(chatId, data));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (messageDispatcher.registerNewUser(chatId)) {
                try {
                    execute(messageDispatcher.sendWelcomeMessage(chatId));
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

    // Method sets commands for the triple-dot menu
    private void setCommands() {
        List<BotCommand> commandsList = new ArrayList<>();
        commandsList.add(new BotCommand(COMMAND_NEXT, "Next"));
        commandsList.add(new BotCommand(COMMAND_PROGRESS, "Statistics"));
        commandsList.add(new BotCommand(COMMAND_RESET, "Reset"));
        commandsList.add(new BotCommand(COMMAND_HELP, "Help"));
        try {
            execute(new SetMyCommands(commandsList));
            execute(new GetMyCommands());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}