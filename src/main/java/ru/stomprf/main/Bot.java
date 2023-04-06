package ru.stomprf.main;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import ru.stomprf.main.util.AppProperties;

import java.io.IOException;

public class Bot {

    public static void main(String[] args) throws IOException {
        final String BOT_TOKEN = AppProperties.getConfig().getProperty("BOT_TOKEN");
        TelegramBot bot = new TelegramBot(BOT_TOKEN);

        bot.setUpdatesListener(updates -> {
            updates.forEach(update -> {
//                long chatId = update.message().chat().id();
//                SendResponse response = bot.execute(new SendMessage(chatId, "Hello!"));
//
//                Path path = Path.of("src/main/resources/music/doja-cat-rules.mp3");
//                bot.execute(new SendAudio(chatId, path.toFile()));
                CallbackQuery callbackQuery = update.callbackQuery();
                if (callbackQuery != null) {
                    System.out.println("Callback message: " + callbackQuery.message().text());
                    System.out.println("Callback data: " + callbackQuery.data());
                    System.out.println("Callback message: " + callbackQuery.from());
                    System.out.println("Callback chat instance: " + callbackQuery.chatInstance());
                    System.out.println("Callback from: " + callbackQuery.from().username());
                } else {
                    long chatId = update.message().chat().id();
                    SendResponse response = bot.execute(new SendMessage(chatId, "Hello!"));

                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup()
                            .addRow(new InlineKeyboardButton("url").url("www.google.com"))
                            .addRow(new InlineKeyboardButton("Doja cat : Like you").callbackData("Doja cat : Like you"))
                            .addRow(new InlineKeyboardButton("Doja cat : Fuck yoy you").callbackData("Doja cat : Fuck you"));
                    bot.execute(new SendMessage(chatId, "Choose favorite track!").replyMarkup(inlineKeyboard));
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
