package ru.stomprf.main;

import io.lindstrom.m3u8.model.KeyMethod;
import io.lindstrom.m3u8.model.MediaPlaylist;
import io.lindstrom.m3u8.model.MediaSegment;
import io.lindstrom.m3u8.model.SegmentKey;
import io.lindstrom.m3u8.parser.MediaPlaylistParser;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Test {

    private final static String INDEX_URL = "https://cs9-3v4.vkuseraudio.net/s/v1/ac/z63vUUhgu-gZRB" +
            "dYEjaVBvWUVILR6Mtpdh0KSMyJyCWLdQmOiyqBHzc-2y7RnvW-pYeuyDZh7iLt0weOMFFQIk6o4WegumItAxXljeZMK" +
            "djRZJ8sv9nZm_6KsSAodM0mlH0UicOlvjjIuvXLxDYzAgZV9LoCwp3XCeQfEY7y-1HsjnU/index.m3u8";
    private final static String PATH_TO_TS_FILE = "./src/main/resources/music/result.ts";

    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        new Test().downloadTS();
    }

    private void downloadTS() throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        MediaPlaylistParser parser = new MediaPlaylistParser();
        MediaPlaylist playlist = parser.readPlaylist(new URL(INDEX_URL).openStream());

        //Get data and parse to convenient format
        List<MediaSegment> segments = playlist.mediaSegments();
        Map<String, SegmentDao> segmentsData = new LinkedHashMap<>();

        segments.forEach(e -> {
            String segmentUri = e.uri();
            Optional<SegmentKey> segmentKey = e.segmentKey();

            if (segmentKey.isPresent()) {
                if (segmentKey.get().method() == KeyMethod.AES_128)
                    segmentsData.put(segmentUri, new SegmentDao(true, segmentKey.get().uri().get()));
                else segmentsData.put(segmentUri, new SegmentDao(false, ""));
            } else
                segmentsData.put(segmentUri, new SegmentDao(false, ""));
        });

        System.out.println("Downloading...");
        byte[] downloadedSegment;
        byte[] key;
        byte[] iv;
        byte[] cipheredData;
        byte[] decryptData;
        BufferedOutputStream bufferedOS = new BufferedOutputStream(new FileOutputStream(PATH_TO_TS_FILE));

        for (Map.Entry<String, SegmentDao> s : segmentsData.entrySet()) {
            //Download segment
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new URL(INDEX_URL.replace("index.m3u8", s.getKey())).openStream());
            downloadedSegment = bufferedInputStream.readAllBytes();
            bufferedInputStream.close();

            if (s.getValue().isHasMethod()) {
                //Download key for segment if exists
                bufferedInputStream = new BufferedInputStream(new URL(s.getValue().getMethodUri()).openStream());
                key = bufferedInputStream.readAllBytes();
                bufferedInputStream.close();

                //Get initialization vector inside downloaded segment
                iv = Arrays.copyOfRange(downloadedSegment, 0, 16);
                cipheredData = Arrays.copyOfRange(downloadedSegment, 16, downloadedSegment.length);

                //Decrypt ciphered data
                decryptData = decrypt("AES/CBC/PKCS5Padding", cipheredData, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
                bufferedOS.write(decryptData);

            } else {
                bufferedOS.write(downloadedSegment);
            }
        }
        System.out.println("All elements are downloaded!");
        System.out.println("Write to file!");
    }

    public static byte[] decrypt(String algorithm, byte[] cipherText, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(cipherText);
    }

    static class SegmentDao{
        private boolean hasMethod;
        private String methodUri;

        public SegmentDao(boolean hasMethod, String methodUri) {
            this.hasMethod = hasMethod;
            this.methodUri = methodUri;
        }

        public boolean isHasMethod() {
            return hasMethod;
        }

        public void setHasMethod(boolean hasMethod) {
            this.hasMethod = hasMethod;
        }

        public String getMethodUri() {
            return methodUri;
        }

        public void setMethodUri(String methodUri) {
            this.methodUri = methodUri;
        }
    }
}
