import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;
import org.junit.Assert;
import org.junit.Test;
import ru.stomprf.main.Track;
import ru.stomprf.main.util.DownloadManager;
import ru.stomprf.main.util.Scrapper;

import java.util.HashSet;
import java.util.Set;


public class HtmlUtilTest {

    private DownloadManager dM = new DownloadManager();
    private Set<Track> trackSet = new HashSet<>();

    @Test
    public void homePage() throws Exception {
        try (final WebClient webClient = new WebClient()) {
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
            final HtmlPage page = webClient.getPage("https://htmlunit.sourceforge.io/");
            Assert.assertEquals("HtmlUnit – Welcome to HtmlUnit", page.getTitleText());
        }
    }

    @Test
    public void checkConnectionAndForms() {
        Scrapper scrapper = new Scrapper();
        System.out.println("Final result: ");
        for (String s : scrapper.scrapLinks("Doja cat", 7)) {
            int arrLength = s.split("/").length;
//            trackSet.add(dM.downloadTrack(s));
            System.out.println("Track name: " + s.split("/")[arrLength - 1]);
        }
        System.out.println("--All tracks download successfully--");
        System.out.println("Show results");
        trackSet.forEach(System.out::println);
    }
}