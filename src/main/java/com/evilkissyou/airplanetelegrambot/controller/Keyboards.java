package com.evilkissyou.airplanetelegrambot.controller;

import static com.evilkissyou.airplanetelegrambot.constants.Constants.*;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Keyboards {
    // Template for inline keyboard
    public InlineKeyboardMarkup getInlineKeyboard(String[][] strings) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowInline = new ArrayList<>();

        for (String[] ss : strings) {
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            for (String s : ss) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(s);
                inlineKeyboardButton.setCallbackData(s);
                keyboardButtonsRow.add(inlineKeyboardButton);
            }
            rowInline.add(keyboardButtonsRow);
        }

        inlineKeyboardMarkup.setKeyboard(rowInline);
        return inlineKeyboardMarkup;
    }

    // Inline keyboard implementation
    public InlineKeyboardMarkup getDefaultInlineKeyboard() {
        return getInlineKeyboard(new String[][]{
                {NEXT},
                {GET_AIRPLANE_INFO}
                }
        );
    }

    // Template for reply keyboard
    public ReplyKeyboardMarkup getKeyboard(String[][] strings) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rowInline = new ArrayList<>();

        for (String[] ss : strings) {
            KeyboardRow row = new KeyboardRow();
            row.addAll(Arrays.asList(ss));
            rowInline.add(row);
        }

        keyboardMarkup.setKeyboard(rowInline);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        return keyboardMarkup;
    }

    // Reply keyboard implementation
    public ReplyKeyboardMarkup getDefaultKeyboard() {
        return getKeyboard(new String[][]{
                {NEXT, PROGRESS},
                {RESET, HELP}
        });
    }
}
