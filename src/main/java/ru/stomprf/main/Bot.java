package ru.stomprf.main;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendAudio;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import ru.stomprf.main.util.AppProperties;

import java.io.IOException;
import java.nio.file.Path;

public class Bot {

    public static void main(String[] args) throws IOException {
        final String BOT_TOKEN = AppProperties.getConfig().getProperty("BOT_TOKEN");
        TelegramBot bot = new TelegramBot(BOT_TOKEN);

        bot.setUpdatesListener(updates -> {
            updates.forEach(update -> {
                long chatId = update.message().chat().id();
                SendResponse response = bot.execute(new SendMessage(chatId, "Hello!"));

                Path path = Path.of("src/main/resources/music/doja-cat-rules.mp3");
                bot.execute(new SendAudio(chatId, path.toFile()));
            });

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
