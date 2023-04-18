package ru.stomprf.main;

import io.lindstrom.m3u8.model.KeyMethod;
import io.lindstrom.m3u8.model.MediaPlaylist;
import io.lindstrom.m3u8.model.MediaSegment;
import io.lindstrom.m3u8.model.SegmentKey;
import io.lindstrom.m3u8.parser.MediaPlaylistParser;
import ru.stomprf.main.scrap.m3u8.SegmentDao;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Test {

    private final static String INDEX_URL = "https://cs9-5v4.vkuseraudio.net/s/v1/ac/iAuNApBaSqkHlDA93zcBpb8O1Br1X71oGSyoRt62" +
            "_ZVtXRCiW0OC5acIsnFIRAb3VEMw7X-CzjZQgfsT-Jv1ynqVucRC6VapC2ZOH1-3LY-xshiVA03L0KbJqbMGC0Zjg0w7oC5IJbv1Do05bXP8casNYQkWk" +
            "-mOLGY2GPra0bBAjqg/index.m3u8";

    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        MediaPlaylistParser parser = new MediaPlaylistParser();

//        MediaPlaylist playlist = parser.readPlaylist(Paths.get("./src/main/resources/index.m3u8"));
        MediaPlaylist playlist = parser.readPlaylist(new URL(INDEX_URL).openStream());

//        System.out.println(parser.writePlaylistAsString(playlist));

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

        // AES cipher generation

        List<String> downloadedSegments = new ArrayList<>();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        System.out.println("Downloading...");
        for (Map.Entry<String, SegmentDao> s: segmentsData.entrySet()) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new URL(INDEX_URL.replace("index.m3u8", s.getKey())).openStream());
            if (s.getValue().isHasMethod()){
                byte[] downloadedSegment = bufferedInputStream.readAllBytes();
                byte[] key = new BufferedInputStream(new URL(s.getValue().getMethodUri()).openStream()).readAllBytes();
                byte[] iv = Arrays.copyOfRange(downloadedSegment, 0, 16);
                byte[] cipheredData = Arrays.copyOfRange(downloadedSegment, 16, downloadedSegment.length);
                byte[] decryptData = decrypt("AES/CBC/PKCS5Padding", cipheredData, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
                outputStream.write(decryptData);
            }
            else {
                outputStream.write(bufferedInputStream.readAllBytes());
            }
            System.out.println("All elements are downloaded!");

            System.out.println("Write to file!");
            Path path = Path.of("./src/main/resources/music/result.ts");
            Files.write(path, outputStream.toByteArray());
//            downloadedSegments.add(new String(bufferedInputStream.readAllBytes(), StandardCharsets.UTF_8));

        }

//        System.out.println(downloadedSegments.get(0));

//        decrypt("AES/CBC/PKCS5Padding", "blabla", new SecretKeySpec(new byte[3], "AES"), new IvParameterSpec(new byte[3]));



// Write playlist to standard out
//        System.out.println(parser.writePlaylistAsString(updated));
    }

    public static byte[] decrypt(String algorithm, byte[] cipherText, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(cipherText);
    }
}
