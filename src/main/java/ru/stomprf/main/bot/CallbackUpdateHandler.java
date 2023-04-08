package ru.stomprf.main.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.SendAudio;
import com.pengrad.telegrambot.request.SendMessage;
import ru.stomprf.main.Track;
import ru.stomprf.main.datacache.UserMusicCache;
import ru.stomprf.main.util.DownloadManager;

import java.nio.file.Path;
import java.util.List;

public class CallbackUpdateHandler {

    public AbstractSendRequest<? extends AbstractSendRequest<?>> handleCallbackUpdate(Update update){
        String data = update.callbackQuery().data();
        Long userId = update.callbackQuery().from().id();
        Long chatId = update.callbackQuery().message().chat().id();
        Path path;
        List<Track> trackList = UserMusicCache.getInstance().getUserTracklist(userId);
        for (Track track: trackList) {
            if (String.valueOf(track.hashCode()).equals(data)){
                DownloadManager.downloadTrack(track);
                path = Path.of(track.getLocation().toString());
                return new SendAudio(chatId, path.toFile());
            }
        }
        return new SendMessage(chatId, data);
    }

}
