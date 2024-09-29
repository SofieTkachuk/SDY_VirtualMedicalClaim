package Utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class SharedDriver {
    private static WebDriver webDriver;

    public enum Browser {
        CHROME
    }
    protected static WebDriver getWebDriver(Browser browser) {
        if (browser == Browser.CHROME) {
            WebDriverManager.chromedriver().setup();
            webDriver = new ChromeDriver();
        }

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        return webDriver;
    }

    protected static void closeDriver() {
        if (webDriver != null) {
            webDriver.quit();

        }
    }
}