package ru.stomprf.main;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendAudio;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

@Component
public class Bot {

    @Autowired
    private Properties properties;

    public static void main(String[] args) throws IOException {
        final String BOT_TOKEN = new Bot().properties.getProperty("BOT_TOKEN");
        TelegramBot bot = new TelegramBot(BOT_TOKEN);

        bot.setUpdatesListener(updates -> {
            updates.forEach(update -> {
                long chatId = update.message().chat().id();
                SendResponse response = bot.execute(new SendMessage(chatId, "Hello!"));

                Path path = Path.of("src/main/resources/yeat-upOfX.mp3");
                bot.execute(new SendAudio(chatId, path.toFile()).title("Up of X").performer("Yeat"));
            });

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
