package PageObjectClasses;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import BasePackage.AbstractBaseClass;

public class DashboardPage extends AbstractBaseClass {
	public enum SideMenuOptions {
		Admin,
		PIM,
		Leave,
		Time,
		Recruitment,
		MyInfo,
		Performance,
		Dashboard,
		Directory,
		Maintenance,
		Clain,
		Buzz
	}
	
	WebDriver driver;
	
	public DashboardPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		waitForScreenToLoad();
	}
	
	public void waitForScreenToLoad() {
		waitForElementToBecomeVisible(By.className("oxd-topbar-header-title"));
	}
	
	@FindBy(xpath = "//ul[@class='oxd-main-menu']//li")
	List<WebElement> mainMenu;
	
	public WebElement setDashboardSideMenuOptions(SideMenuOptions option) {
			return mainMenu.stream().
					filter(li -> li.getText()
							.trim().equalsIgnoreCase(option.name()))
							.findFirst()
							.orElseThrow(() -> new RuntimeException("Menu option not found: " + option));
	}
	
	public void clickOnSideOption(SideMenuOptions option) {
		setDashboardSideMenuOptions(option).click();
	}
}