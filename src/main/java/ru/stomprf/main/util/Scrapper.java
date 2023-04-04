package ru.stomprf.main.util;

import org.htmlunit.WebClient;
import org.htmlunit.html.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Scrapper {

    private HtmlPage page;
    private WebClient client;
    private List<String> links;
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
            properties.load(new FileInputStream("src/main/resources/app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> scrapLinks(String searchText, int numberOfTracks) {
        String[] searchElements = searchText.split(" ");
        StringBuilder urlSearch = new StringBuilder();
        for (String searchElement : searchElements) {
            urlSearch.append(searchElement).append("%20");
        }
        try {
            System.out.println(properties.getProperty("PATTERN") + urlSearch);
//            page = client.getPage(properties.getProperty("PATTERN") + "doja%20cat");
            page = client.getPage(properties.getProperty("PATTERN") + urlSearch);
            List<HtmlDivision> divList = page.getByXPath("//div[contains(@class, \"playlist\")]//div[@data-url]");

            if (divList.size() == 0) {
                System.out.println("No search results");
                return links;
            }
            links = extractLinks(divList);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return links.subList(0, numberOfTracks);
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
//        links.forEach(System.out::println);

        return links;
    }
}

