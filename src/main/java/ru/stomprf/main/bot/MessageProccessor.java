package ru.stomprf.main.bot;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import ru.stomprf.main.Track;
import ru.stomprf.main.datacache.UserMusicCache;
import ru.stomprf.main.util.Scrapper;
import ru.stomprf.main.util.ScrapperKeyboard;

import java.util.List;
import java.util.Locale;

public class MessageProccessor {

    public SendMessage proccessInputMessage(Message message, BotState botState){
        SendMessage sendMessage;
        Long chatId = message.chat().id();
        Long userId = message.from().id();
        switch (botState){
            case ASK_TITLE:
                sendMessage = new SendMessage(chatId,
                        String.format("Hey, %s! Show me your loved track ;)", message.from().firstName()));
                break;
            case SHOW_TRACKLIST:
                System.out.println("Proccessor id " + userId);
                List<Track> trackList = new Scrapper().scrapTracks(message.text().
                        toLowerCase(Locale.ROOT).trim(), 5);
                UserMusicCache.getInstance().setUserTrackList(userId, trackList);
                sendMessage = new SendMessage(message.chat().id(), "Choose the right one: ")
                        .replyMarkup(
                                new ScrapperKeyboard().createTrackKeyboard(
                                trackList));
                break;
            default:
                sendMessage = null;
                break;
        }

    return sendMessage;
    }

}
