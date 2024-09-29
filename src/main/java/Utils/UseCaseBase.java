package Utils;

import Pages.BasePage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UseCaseBase {
    public static WebDriver webDriver;
    protected static BasePage page;

    @BeforeAll
    public static void setupMain() {
        page = new BasePage();
        webDriver = SharedDriver.getWebDriver(SharedDriver.Browser.CHROME);
        page.setWebDriver(webDriver);
    }

    protected void waitForElementVisible(Duration duration, By locator) {
        WebDriverWait wait = new WebDriverWait(webDriver, duration);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    protected void waitForElementClickable(By locator, Duration duration) {
        WebDriverWait wait = new WebDriverWait(webDriver, duration);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForAttributeValue(WebElement element, String attributeName, String expectedValue, Duration timeout) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeout);
        wait.until(ExpectedConditions.attributeToBe(element, attributeName, expectedValue));
    }

    @AfterAll
    public static void tearDownMain() {
        SharedDriver.closeDriver();
        webDriver = null;

    }
}
