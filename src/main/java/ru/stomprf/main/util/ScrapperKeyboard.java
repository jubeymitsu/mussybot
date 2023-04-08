package ru.stomprf.main.util;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import ru.stomprf.main.Track;

import java.util.List;

public class ScrapperKeyboard {

    public InlineKeyboardMarkup createTrackKeyboard(List<Track> tracks){
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        System.out.println("Inside method: ");
        tracks.forEach(System.out::println);

        for (Track track : tracks) {
            inlineKeyboard.addRow(new InlineKeyboardButton(track.getTitle())
                    .callbackData(String.valueOf(track.hashCode())));
        }
        return inlineKeyboard;
    }

}
