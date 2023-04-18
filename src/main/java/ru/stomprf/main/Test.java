package ru.stomprf.main;

import io.lindstrom.m3u8.model.KeyMethod;
import io.lindstrom.m3u8.model.MediaPlaylist;
import io.lindstrom.m3u8.model.MediaSegment;
import io.lindstrom.m3u8.model.SegmentKey;
import io.lindstrom.m3u8.parser.MediaPlaylistParser;
import ru.stomprf.main.scrap.m3u8.SegmentDao;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Test {

    private final static String INDEX_URL = "";

    public static void main(String[] args) throws IOException {

        MediaPlaylistParser parser = new MediaPlaylistParser();

//        MediaPlaylist playlist = parser.readPlaylist(Paths.get("./src/main/resources/index.m3u8"));
        MediaPlaylist playlist = parser.readPlaylist(new URL(INDEX_URL).openStream());

        System.out.println(parser.writePlaylistAsString(playlist));

        //Get data and parse to convenient format
        List<MediaSegment> segments = playlist.mediaSegments();
        Map<String, SegmentDao> segmentsData = new LinkedHashMap<>();

        segments.forEach(e -> {
            String segmentUri = e.uri();
            Optional<SegmentKey> segmentKey = e.segmentKey();

            if (segmentKey.isPresent()){
                if (segmentKey.get().method() == KeyMethod.AES_128)
                    segmentsData.put(segmentUri, new SegmentDao(true, segmentKey.get().uri().get()));
                else segmentsData.put(segmentUri, new SegmentDao(false, ""));
            }
            else
                segmentsData.put(segmentUri, new SegmentDao(false, ""));
        });

        //C H E C K
//        for (Map.Entry<String, SegmentDao> s: segmentsData.entrySet()) {
//            System.out.println("-> " + s.getKey());
//        }

        List<String> downloadedSegments = new ArrayList<>();

        for (Map.Entry<String, SegmentDao> s: segmentsData.entrySet()) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new URL(INDEX_URL.replace("music/index.m3u8", s.getKey())).openStream());
            downloadedSegments.add(new String(bufferedInputStream.readAllBytes(), StandardCharsets.UTF_8));

        }
        System.out.println("All elements are downloaded...");
        System.out.println(downloadedSegments.get(0));



// Write playlist to standard out
//        System.out.println(parser.writePlaylistAsString(updated));
    }
}
