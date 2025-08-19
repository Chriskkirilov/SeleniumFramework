package PageObjectClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import BasePackage.AbstractBaseClass;

public class PIMEmployeeListPage extends AbstractBaseClass {

	WebDriver driver;
	
	public PIMEmployeeListPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		waitForScreenToLoad("PIM");
	}
	
	@FindBy(css = "div.orangehrm-header-container button")
	WebElement addButton;
	
	public void clickAddButton() {
		addButton.click();
	}
}
