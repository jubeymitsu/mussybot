package ru.stomprf.main.util;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlElement;
import org.htmlunit.html.HtmlForm;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlTextInput;
import org.htmlunit.javascript.host.event.KeyboardEvent;
import ru.stomprf.main.Track;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Scrapper {

    private HtmlPage page;

    public Scrapper() {

        Properties properties = new Properties();


        try (final WebClient webClient = new WebClient()) {
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
            properties.load(new FileInputStream("src/main/resources/app.properties"));
            String MUSIC_URL =  properties.getProperty("MUSIC_URL");
            System.out.println(MUSIC_URL);
            this.page = webClient.getPage(MUSIC_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Track> findFiveTracks(String search){
        List<Track> tracks = new ArrayList<>();
        try {
            Path path = Path.of("src/main/resources/musify_output.txt");
            HtmlForm form = (HtmlForm) page.getByXPath("//form").get(0);


            HtmlElement button = (HtmlElement) page.createElement("button");
            button.setAttribute("type", "submit");
            form.appendChild(button);
            HtmlTextInput searchField = form.getInputByName("SearchText");
            searchField.click();
            searchField.type(search);
            searchField.type(13);
//            button.click();

            Thread.sleep(500);
            Files.writeString(path, page.asNormalizedText());
            int length = page.getByXPath("//data-artist").size();
            System.out.println("Doja cat values:" + length);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
//        return null;
    }

}

