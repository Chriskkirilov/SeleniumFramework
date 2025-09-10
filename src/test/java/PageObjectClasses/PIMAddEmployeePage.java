package PageObjectClasses;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import BasePackage.AbstractBaseClass;

public class PIMAddEmployeePage extends AbstractBaseClass {
	
	WebDriver driver;
	
    public enum UserStatus { Enabled, Disabled }

	public PIMAddEmployeePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		waitForElementToBecomeVisible(By.cssSelector(".oxd-text.oxd-text--h6.orangehrm-main-title"));
	}
	
    @FindBy(css = "input[placeholder='First Name']")
	WebElement firstNameField;
	
    @FindBy(css = "input[placeholder='Middle Name']")
	WebElement middleNameField;
	
    @FindBy(css = "input[placeholder='Last Name']")
	WebElement lastNameField;
	
    @FindBy(css = "input.oxd-input")
	List<WebElement> inputFieldsList;

	@FindBy(xpath = "(//span[@class='oxd-switch-input oxd-switch-input--active --label-right'])")
	WebElement switchLoginDetails;
	
	@FindBy(className = "oxd-radio-input")
	List<WebElement> radioOptions;
	
	@FindBy(css = "button[type='submit']")
	WebElement submitAddUserButton;
	
	public void fillAddEmployeeForm(String firstName, String middleName, String lastName, String employeeId) {
	    waitForElementToBecomeVisible(By.className("orangehrm-card-container"));

	    waits.clickable(firstNameField).click();
	    firstNameField.sendKeys(firstName);

	    waits.clickable(middleNameField).click();
	    middleNameField.sendKeys(middleName);

	    waits.clickable(lastNameField).click();
	    lastNameField.clear();
	    lastNameField.sendKeys(lastName);

	    WebElement employeeIdField = inputFieldsList.get(4); // keep for now; we’ll refactor locators later
	    employeeIdField.sendKeys(Keys.chord(Keys.COMMAND, "a", Keys.DELETE));
	    employeeIdField.sendKeys(employeeId);
	}

	public void createLoginDetails(String username, String password, UserStatus status) {
	    AddCreateLoginDetails();

	    WebElement usernameField = inputFieldsList.get(5);
	    WebElement passwordField = inputFieldsList.get(6);
	    WebElement confirmPasswordField = inputFieldsList.get(7);

	    usernameField.sendKeys(username);
	    passwordField.sendKeys(password);
	    confirmPasswordField.sendKeys(password);

	    switch (status) {
	        case Enabled: 
	        	radioOptions.get(0).click();
	        case Disabled: 
	        	radioOptions.get(1).click();
	    }
	}

	
	public void submitAddUser() {
		submitAddUserButton.click();
	}
	
	public void AddCreateLoginDetails() {
		switchLoginDetails.click();
	}
	
}
