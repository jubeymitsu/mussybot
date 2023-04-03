
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
    public void checkConnectionAndForms(){
        Scrapper scrapper = new Scrapper();
        System.out.println("Final result: ");
        for (String s: scrapper.scrapLinks("Doja cat", 5)) {
            System.out.println(s);
        }
    }
}
