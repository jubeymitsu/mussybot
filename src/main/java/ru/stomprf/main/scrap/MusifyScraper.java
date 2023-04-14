package ru.stomprf.main.scrap;

import org.htmlunit.WebClient;
import org.htmlunit.html.*;
import ru.stomprf.main.Track;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class MusifyScraper implements Scraper{

    private HtmlPage page;
    private WebClient client;
    private List<Track> tracks = new ArrayList<>();
    private List<String> links = new ArrayList<>();
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream("src/main/resources/app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final String MUSIC_SOURCE_URL = properties.getProperty("MUSIC_URL");
    private final String DOWNLOAD_PATH = properties.getProperty("DOWNLOAD_PATH");

    public MusifyScraper() {
        this.client = new WebClient();
        this.client.getOptions().setCssEnabled(false);
        this.client.getOptions().setJavaScriptEnabled(false);
        try {
            properties.load(new FileInputStream("src/main/resources/app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Track> scrapTracks(String searchText, int preferedNumberOfTracks) {
        String[] searchElements = searchText.split(" ");
        StringBuilder urlSearch = new StringBuilder();

        for (String searchElement : searchElements) {
            urlSearch.append(searchElement).append("%20");
        }
        try {
            System.out.println(properties.getProperty("PATTERN") + urlSearch);
            page = client.getPage(properties.getProperty("PATTERN") + urlSearch);
            List<HtmlDivision> divList = page.getByXPath("//div[contains(@class, \"playlist\")]//div[@data-url]");

            if (divList.size() >= preferedNumberOfTracks)
                return extractData(divList).subList(0, preferedNumberOfTracks);
            else if (divList.size() == 0){
                System.out.println("No search results");
                return tracks;
            }
            else
                return extractData(divList);

        } catch (IOException e) {
            e.printStackTrace();
        }
      return tracks;
    }

    private List<Track> extractData(List<HtmlDivision> divisions) {
        for (HtmlDivision elem : divisions) {
            //format type: HtmlDivision[<div id="" class="" data-url="/track" data-="" ..etc.]\
            tracks.add(buildTrack(elem.toString()));
        }
        return tracks;
    }

    private Track buildTrack(String div){
        String trackTitle = div.split("data-title=")[1].split("\"")[1];
        String trackLink = div.split(" ")[4].split("\"")[1];
        return new Track(trackTitle, MUSIC_SOURCE_URL + trackLink, Path.of(DOWNLOAD_PATH + trackTitle + ".mp3"));
    }
}

