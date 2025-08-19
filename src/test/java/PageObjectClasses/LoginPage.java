package PageObjectClasses;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import BasePackage.AbstractBaseClass;

public class LoginPage extends AbstractBaseClass {
	
	WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = "div[class^='oxd-sheet']")
	WebElement loginSheetContainer;
	
	@FindBy(name = "username")
	WebElement usernameField;
	
	@FindBy(name = "password")
	WebElement passwordField;
	
	@FindBy(css = "button[type='submit']")
	WebElement submitLoginButton;

	public List<WebElement> getParagraphs() {
	    return loginSheetContainer.findElements(By.tagName("p"));
	}
	
	public String[] extractLoginCredentialsFromString() {
	    List<WebElement> paragraphs = getParagraphs();
	    return paragraphs.stream()
                .map(p -> p.getText().split(":", 2)[1].trim())
                .toArray(String[]::new);
	}
	
	public void fillLoginForm(String username, String password) {
		usernameField.sendKeys(username);
        passwordField.sendKeys(password);
	}
	
	public void submitLogin() {
		submitLoginButton.click();
	}
}
