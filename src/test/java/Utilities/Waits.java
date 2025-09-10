package Utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class Waits {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public Waits(WebDriver driver) {
        this(driver, Duration.ofSeconds(10));
    }

    public Waits(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
        this.wait.ignoring(NoSuchElementException.class)
                 .ignoring(StaleElementReferenceException.class);
    }

    // Generic
    public <V> V until(Function<? super WebDriver, V> condition) {
        return wait.until(condition);
    }

    // Visibility / Clickable (By)
    public WebElement visible(By by) { return until(ExpectedConditions.visibilityOfElementLocated(by)); }
    public List<WebElement> allVisible(By by) { return until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by)); }
    public WebElement clickable(By by) { return until(ExpectedConditions.elementToBeClickable(by)); }
    public boolean invisible(By by) { return until(ExpectedConditions.invisibilityOfElementLocated(by)); }

    // Visibility / Clickable (WebElement overloads – handy for @FindBy)
    public WebElement visible(WebElement el) { return until(ExpectedConditions.visibilityOf(el)); }
    public WebElement clickable(WebElement el) { return until(ExpectedConditions.elementToBeClickable(el)); }
    public boolean invisible(WebElement el) { return until(ExpectedConditions.invisibilityOf(el)); }

    // Text / Attribute
    public boolean textToBe(By by, String text) { return until(ExpectedConditions.textToBe(by, text)); }
    public boolean textToContain(By by, String fragment) { return until(ExpectedConditions.textToBePresentInElementLocated(by, fragment)); }
    public boolean textNotPresentIn(WebElement el, String text) {
        return until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(el, text)));
    }
    public boolean attributeToBe(By by, String attr, String value) { return until(ExpectedConditions.attributeToBe(by, attr, value)); }

    // URL / Document
    public boolean urlContains(String fragment) { return until(ExpectedConditions.urlContains(fragment)); }
    public void domReady() {
        until(d -> "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState")));
    }
}
