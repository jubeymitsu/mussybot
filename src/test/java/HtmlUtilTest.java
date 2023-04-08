import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;
import org.junit.Assert;
import org.junit.Test;
import ru.stomprf.main.Track;
import ru.stomprf.main.util.DownloadManager;
import ru.stomprf.main.util.Scrapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class HtmlUtilTest {

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
        System.out.println("Test started.");
        Scrapper scrapper = new Scrapper();
        List<Track> trackList = scrapper.scrapTracks("билан молния", 7);
        if (!trackList.isEmpty()){
            trackList.forEach(System.out::println);
            System.out.println("Download track...");
            DownloadManager.downloadTrack(trackList.get(0));
        }
        else
            System.out.println("Tracklist empty :(");
    }
}