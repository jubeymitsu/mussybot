package ru.stomprf.main;


import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.protocol.BasicHttpContext;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Test {

    static String FILE_URL = "https://musify.club/track/dl/12711722/doja-cat-bottom-bitch.mp3";
    static String FILE_NAME = "path-to-source/doja_cat-track.mp3";

    public static void main(String[] args) throws IOException {

//        File fileToSave = new File("/doja-track");
//        String downloadLink = "https://musify.club/track/dl/12711722/doja-cat-bottom-bitch.mp3";
////Download file using default org.apache.http client
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet(downloadLink);
//        HttpResponse response = httpClient.execute(httpGet, new BasicHttpContext());
//        System.out.println(response.getCode());
////Save file on disk
////        copyInputStreamToFile(response.getEntity().getContent(), fileToSave);

        try (BufferedInputStream in = new BufferedInputStream(new URL(FILE_URL).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            // handle exception
        }

    }

}
