package ru.stomprf.main.util;

import org.htmlunit.WebClient;
import org.htmlunit.html.*;
import ru.stomprf.main.Track;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Scrapper {

    private HtmlPage page;
    private WebClient client;
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream("src/main/resources/app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public List<Track> findFiveTracks(String searchText) {

        String[] searchElements = searchText.split(" ");
        StringBuilder urlSearch = new StringBuilder();
        for (int i = 0; i < searchElements.length; i++) {
            urlSearch.append(searchElements[i]).append("%20");
        }
        List<Track> tracks = new ArrayList<>();
        try {
            System.out.println(properties.getProperty("PATTERN") + "doja%20cat");
            page = client.getPage(properties.getProperty("PATTERN") + "doja%20cat");
            List<HtmlDivision> list = page.getByXPath("//div[contains(@class, \"playlist\")]//div[@data-url]");

            new DownloadManager().downloadTrack(extractLinks(list).get(2));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> extractLinks(List<HtmlDivision> divisions) {
        ArrayList<String> links = new ArrayList<>();
        String musicSourceUrl = properties.getProperty("MUSIC_URL");
        for (HtmlDivision elem : divisions) {
            //format type: HtmlDivision[<div id="" class="" data-url="/track" data-="" ..etc.]
            String trackSubLink = elem.toString().split(" ")[4].split("\"")[1];
            String completeUrl = musicSourceUrl + trackSubLink;
            links.add(completeUrl);
        }
        System.out.println("List length: //" + links.size());

        links.forEach(System.out::println);

        return links;
    }
}

