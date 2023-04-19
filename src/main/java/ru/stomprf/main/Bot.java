package ru.stomprf.main;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendAudio;
import com.pengrad.telegrambot.request.SendMessage;
import ru.stomprf.main.util.AppProperties;

import java.io.IOException;
import java.nio.file.Path;

public class Bot {

    public static void main(String[] args) throws IOException {
        final String BOT_TOKEN = AppProperties.getConfig().getProperty("BOT_TOKEN");
        TelegramBot bot = new TelegramBot(BOT_TOKEN);

        bot.setUpdatesListener(updates -> {
            updates.forEach(update -> {
                Long chatId = update.message().chat().id();
//
//                AbstractSendRequest message;
//                if (update.callbackQuery() != null){
//                    message = new CallbackUpdateHandler().handleCallbackUpdate(update);
//                }
//                else
//                    message = new UpdateHandler().handleUpdate(update);
                Path path = Path.of("src/main/resources/music/result.ts");
                System.out.println(path.toString());
                bot.execute(new SendMessage(chatId, "Hey! :?"));
                bot.execute(new SendAudio(chatId, path.toFile()));
                System.out.println("done");
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
