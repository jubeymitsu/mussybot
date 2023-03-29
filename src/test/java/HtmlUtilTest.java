
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;
import org.junit.Assert;
import org.junit.Test;
import ru.stomprf.main.util.Scrapper;


public class HtmlUtilTest {

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
    public void checkConnecntionAndForms(){
        Scrapper scrapper = new Scrapper();
        scrapper.findFiveTracks("Doja cat");
    }
}
