package BasePackage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractBaseClass {
	
	WebDriver driver;
	
	public AbstractBaseClass(WebDriver driver) {
		this.driver = driver;
	}
	
	public void waitForElementToBecomeVisible(By FindBy) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
		wait.until(ExpectedConditions.visibilityOfElementLocated(FindBy));
	}
	
	public void waitForElementByMatchingText(By FindBy, String expectedText) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
		wait.until(ExpectedConditions.textToBe(FindBy, expectedText));
	}
	
	public void waitForScreenToLoad(String headerTitle) {
		waitForElementByMatchingText(By.xpath("(//div[@class='oxd-topbar-header-title'])[1]"), headerTitle);
	}

}
