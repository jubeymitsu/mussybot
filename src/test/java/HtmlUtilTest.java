
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;
import org.junit.Assert;
import org.junit.Test;

public class HtmlUtilTest {

    @Test
    public void homePage() throws Exception {
        System.setProperty("org.apache.commons.logging.Log",
                "org.apache.commons.logging.impl.NoOpLog");
        try (final WebClient webClient = new WebClient()) {
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
            final HtmlPage page = webClient.getPage("https://htmlunit.sourceforge.io/");
            Assert.assertEquals("HtmlUnit – Welcome to HtmlUnit", page.getTitleText());
        }
    }
}
