package ru.stomprf.main;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage("https://htmlunit.sourceforge.io/");
//            Assert.assertEquals("HtmlUnit – Welcome to HtmlUnit", page.getTitleText());
            System.out.println(page.getTitleText());
        } catch (Exception ec) {

        }
    }
}
