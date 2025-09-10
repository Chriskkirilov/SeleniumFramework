package BasePackage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.Waits;

public class AbstractBaseClass {

    protected final WebDriver driver;
    protected final Waits waits;

    private static final By TOPBAR_TITLE = By.cssSelector(".oxd-topbar-header-title");
    private static final By SUCCESS_TOAST = By.cssSelector(".oxd-toast.oxd-toast--success");

    public AbstractBaseClass(WebDriver driver) {
        this.driver = driver;
        this.waits = new Waits(driver, Duration.ofSeconds(10));
    }

    public void waitForElementToBecomeVisible(By by) {
        waits.visible(by);
    }

    public void waitForElementByMatchingText(By by, String expectedText) {
        waits.textToBe(by, expectedText);
    }

    public void waitForScreenToLoad() {
        waits.domReady();
        waits.visible(TOPBAR_TITLE);
    }

    public void waitForScreenToLoad(String headerTitle) {
        waits.textToContain(TOPBAR_TITLE, headerTitle);
    }

    public void waitForSuccessToast() {
        waits.visible(SUCCESS_TOAST);
        waits.invisible(SUCCESS_TOAST);
    }
}
