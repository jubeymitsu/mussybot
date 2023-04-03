package ru.stomprf.main.util;

import ru.stomprf.main.Track;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;

public class DownloadManager {

    public Track downloadTrack(String trackUrl) {
        int arrLength = trackUrl.split("/").length;
        String trackTitle = trackUrl.split("/")[arrLength - 1];
        String FILE_URL = String.format("src/main/resources/music/%s", trackTitle);
        Path downloadedFilePath = Path.of(FILE_URL);
        try {
            ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(trackUrl).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_URL);
            FileChannel fileChannel = fileOutputStream.getChannel();
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Track(trackTitle, downloadedFilePath);
    }
}

