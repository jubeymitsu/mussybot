package ru.stomprf.main.bot;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import ru.stomprf.main.util.Scrapper;
import ru.stomprf.main.util.ScrapperKeyboard;

import java.util.Locale;

public class MessageProccessor {

    public SendMessage proccessInputMessage(Message message, BotState botState){
        SendMessage sendMessage;
        switch (botState){
            case ASK_TITLE:
                sendMessage = new SendMessage(message.chat().id(),
                        String.format("Hey, %s! Show me your loved track ;)", message.from().firstName()));
                break;
            case SHOW_TRACKLIST:
                System.out.println("Im here!");
                sendMessage = new SendMessage(message.chat().id(), "Choose the right one: ")
                        .replyMarkup(
                                new ScrapperKeyboard().createTrackKeyboard(
                                new Scrapper().scrapLinks(message.text().
                                        toLowerCase(Locale.ROOT).trim(), 5)));
                break;
            default:
                sendMessage = null;
                break;
        }

    return sendMessage;
    }

}
