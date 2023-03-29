package ru.stomprf.main;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.Properties;

public class Test {

    private Properties properties;

    //Update later
//    private String FILE_URL =  properties.getProperty("MUSIC_PATH") + "doja-cat-bottom-bitch.mp3";
    private String FILE_URL =  "doja-cat-bottom-bitch.mp3";
    private String FILE_NAME = "/Users/macbookpro/IdeaProjects/music_bot/src/main/resources/music/doja_cat-track.mp3";

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


        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage("https://htmlunit.sourceforge.io/");
//            Assert.assertEquals("HtmlUnit – Welcome to HtmlUnit", page.getTitleText());
            System.out.println(page.getTitleText());

        }
        catch (Exception ec){

        }

//      D O W N L O A D
//        try (BufferedInputStream in = new BufferedInputStream(new URL(FILE_URL).openStream());
//             FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME)) {
//            byte dataBuffer[] = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
//                fileOutputStream.write(dataBuffer, 0, bytesRead);
//            }
//        } catch (IOException e) {
//            // handle exception
//        }

    }

}
