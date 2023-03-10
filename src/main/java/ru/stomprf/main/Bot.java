package ru.stomprf.main;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Bot {

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/app.properties"));
        final String BOT_TOKEN = properties.getProperty("BOT_TOKEN");
        TelegramBot bot = new TelegramBot(BOT_TOKEN);

        bot.setUpdatesListener(updates -> {

            updates.forEach(update -> {
                long chatId = update.message().chat().id();
                SendResponse response = bot.execute(new SendMessage(chatId, "Hello!"));
            });

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
