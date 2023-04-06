package ru.stomprf.main.util;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

import java.util.Arrays;
import java.util.List;

public class ScrapperKeyboard {

    public InlineKeyboardMarkup createTrackKeyboard(List<String> linksData){
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        System.out.println("Inside method: ");
        linksData.forEach(System.out::println);
        for (String trackLink : linksData) {
            int arrLength = trackLink.split("/").length;
            inlineKeyboard.addRow(new InlineKeyboardButton(trackLink.split("/")[arrLength - 1])
                    .callbackData("s"));
            System.out.println("BYTES" + Arrays.toString(trackLink.getBytes()));
        }
        return inlineKeyboard;
    }

}
