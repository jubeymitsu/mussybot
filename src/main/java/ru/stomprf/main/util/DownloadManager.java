package ru.stomprf.main.util;

import ru.stomprf.main.Track;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class DownloadManager {

    public static boolean downloadTrack(Track track) {
        try {
            ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(track.getDownloadLink()).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(track.getLocation().toFile());
            FileChannel fileChannel = fileOutputStream.getChannel();
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("Download track!");
        return true;
    }
}

