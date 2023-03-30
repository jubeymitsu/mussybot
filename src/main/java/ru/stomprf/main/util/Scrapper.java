package ru.stomprf.main.util;

import org.htmlunit.WebClient;
import org.htmlunit.html.*;
import ru.stomprf.main.Track;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Scrapper {

    private HtmlPage page;
    private WebClient client;
    private Properties properties = new Properties();

    public Scrapper() {
        this.client = new WebClient();
        this.client.getOptions().setCssEnabled(false);
        this.client.getOptions().setJavaScriptEnabled(false);
        try {
            this.properties.load(new FileInputStream("src/main/resources/app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Track> findFiveTracks(String search) {
        List<Track> tracks = new ArrayList<>();
        try {
            System.out.println(properties.getProperty("PATTERN") + "doja%20cat");
            page = client.getPage(properties.getProperty("PATTERN") + "doja%20cat");
            List<HtmlDivision> list = page.getByXPath("//div[contains(@class, \"playlist\")]//div[@data-url]");
            downloadTrack(extractLinks(list).get(0));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> extractLinks(List<HtmlDivision> divisions) {
        ArrayList<String> list = new ArrayList<>();
        for (HtmlDivision elem : divisions) {
            //format type: HtmlDivision[<div id="" class="" data-url="/track" data-="" ..etc.]
            String link = elem.toString().split(" ")[4].split("\"")[1];
            list.add(link);
        }
        System.out.println("List length: //" + list.size());
        list.forEach(System.out::println);

        return list;
    }

    private void downloadTrack(String trackLink) {
        String musicSourceUrl = properties.getProperty("MUSIC_URL");
        //format type: /1*track/2*play/3*12711720/4*doja-cat-wont-bite-feat-smino.mp3
        String FILE_URL = String.format("src/main/resources/music/%s", trackLink.split("/")[4]);
        String COMPLETE_URL = musicSourceUrl + trackLink;

        try (BufferedInputStream in = new BufferedInputStream(new URL(COMPLETE_URL).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(FILE_URL)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

