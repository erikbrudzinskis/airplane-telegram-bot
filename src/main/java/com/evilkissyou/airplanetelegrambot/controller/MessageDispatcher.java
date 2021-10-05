package com.evilkissyou.airplanetelegrambot.controller;

import static com.evilkissyou.airplanetelegrambot.constants.Constants.*;
import com.evilkissyou.airplanetelegrambot.data.Airplane;
import com.evilkissyou.airplanetelegrambot.data.AirplaneRepository;
import com.evilkissyou.airplanetelegrambot.data.User;
import com.evilkissyou.airplanetelegrambot.data.UsersRepository;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.List;


public class MessageDispatcher {

    final AirplaneRepository airplaneRepository;
    final UsersRepository usersRepository;
    final Keyboards keyboards;

    public static int maxAirplaneNumber;
    private int airplaneId;
    private List<Airplane> CompletedAirplaneList;
    private List<Integer> CompletedAirplaneIdList;

    public MessageDispatcher(AirplaneRepository airplaneRepository, UsersRepository usersRepository, Keyboards keyboards) {
        this.airplaneRepository = airplaneRepository;
        this.usersRepository = usersRepository;
        this.keyboards = keyboards;
    }

    // Method handles all the main messages from user to bot
    public SendMessage getResponseByRegularMessage(String chatId, String messageText) {
        User user = usersRepository.findByChatId(Integer.parseInt(chatId));
        CompletedAirplaneList = user.getAirplanes();
        CompletedAirplaneIdList = user.getCompletedAirplanesIds();
        SendMessage outMessage = new SendMessage();
        outMessage.setChatId(chatId);
        String progress = String.format("Progress: %.1f%% (%d/%d)",
                CompletedAirplaneIdList.size() * 100.0 / maxAirplaneNumber,
                CompletedAirplaneIdList.size(),
                maxAirplaneNumber);
        ReplyKeyboard keyboard = keyboards.getDefaultKeyboard();

        String text;
        switch (messageText) {
            case NEXT:
            case COMMAND_NEXT:
                Airplane airplane = findNewRandomAirplane();
                if (airplane == null) {
                    text = "No more airplanes left! Please reset!";
                } else {
                    text = airplane.getName();
                    keyboard = keyboards.getDefaultInlineKeyboard();
                }
                usersRepository.save(user);
                break;
            case PROGRESS:
            case COMMAND_PROGRESS:
                text = progress;
                keyboard = keyboards.getDefaultKeyboard();
                break;
            case RESET:
            case COMMAND_RESET:
                usersRepository.delete(user);
                text = "Reset completed!";
                keyboard = keyboards.getDefaultKeyboard();
                break;
            case HELP:
            case COMMAND_HELP:
                text = DESCRIPTION;
                keyboard = keyboards.getDefaultKeyboard();
                break;
            default:
                text = "Bad request...";
                keyboard = keyboards.getDefaultKeyboard();
        }


        outMessage.setParseMode("HTML");
        outMessage.setReplyMarkup(keyboard);
        outMessage.setText(text);
        return outMessage;
    }

    // Method handles the input from inline keyboard after an airplane was shown
    public SendMessage getResponseByCallback(String chatId, String messageText) {
        SendMessage outMessage = new SendMessage().setChatId(chatId);
        Airplane airplane;
        if (airplaneId == 0) {
            User user = usersRepository.findByChatId(Integer.parseInt(chatId));
            CompletedAirplaneList = user.getAirplanes();
            CompletedAirplaneIdList = user.getCompletedAirplanesIds();
            airplane = findNewRandomAirplane();
        } else {
            airplane = airplaneRepository.findById(airplaneId).get();
        }
        String text;
        ReplyKeyboard keyboard = keyboards.getDefaultKeyboard();

        switch (messageText) {
            case GET_AIRPLANE_INFO:
                text = airplane.toString();
                break;
            case NEXT:
                SendMessage response = getResponseByRegularMessage(chatId, COMMAND_NEXT);
                text = response.getText();
                keyboard = response.getReplyMarkup();
                break;
            default:
                text = "Bad request";
        }
        outMessage.setParseMode("HTML");
        outMessage.setText(text);
        outMessage.setReplyMarkup(keyboard);
        return outMessage;
    }

    public boolean registerNewUser(String chatId) {
        if (!usersRepository.existsUserByChatId(Integer.parseInt(chatId))) {
            User user = new User(Integer.parseInt(chatId));
            usersRepository.save(user);
            return true;
        }
        return false;
    }

    public SendMessage sendWelcomeMessage(String chatId) {
        return new SendMessage(chatId, WELCOME).setReplyMarkup(keyboards.getDefaultKeyboard());
    }

    private Airplane findNewRandomAirplane() {
        if (CompletedAirplaneList.size() >= airplaneRepository.count()) {
            return null;
        } else {
            Airplane airplane;
            do {
                airplaneId = (int) (Math.random() * maxAirplaneNumber) + 1;
            } while (CompletedAirplaneIdList.contains(airplaneId));
            airplane = airplaneRepository.findById(airplaneId).get();
            CompletedAirplaneList.add(airplane);
            return airplane;
        }
    }
}
