package PageObjectClasses;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import BasePackage.AbstractBaseClass;

public class AdminPage extends AbstractBaseClass {
	
	WebDriver driver;

	public AdminPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = "div.orangehrm-header-container button")
	WebElement addButton;
	
	@FindBy(xpath = "(//div[@class='oxd-select-text oxd-select-text--active'])")
	List<WebElement> dropdowns;

	@FindBy(css = "div.oxd-select-dropdown")
	WebElement dropdownWrapper;

	@FindBy(xpath = "//div[@class='oxd-autocomplete-wrapper']//div//input")
	WebElement employeeNameField;
	
	@FindBy(css = "button[type='submit']")
	WebElement submitLoginButton;
	
    @FindBy(css = "input.oxd-input")
	List<WebElement> inputFieldsList;
    
	public void clickAddButton() {
		addButton.click();
	}
	
	public void waitForAdminFormToAppear() {
		waitForElementToBecomeVisible(By.className("orangehrm-card-container"));
	}
	
	public void selectDropdownOption(String optionText) {
        List<WebElement> options = dropdownWrapper.findElements(By.cssSelector("div.oxd-select-dropdown div.oxd-select-option"));
        options.stream()
        .filter(option
        		-> option.getText().trim().equalsIgnoreCase(optionText))
        .findFirst().orElseThrow(()
        		-> new RuntimeException("Option not found: " + optionText))
        .click();

	}
	
	public void selectUserRoleOption(String optionText) throws InterruptedException {
		waitForScreenToLoad("Admin");
		WebElement userRoleDropdown = dropdowns.get(0);

		userRoleDropdown.click();
		selectDropdownOption(optionText);
	}
	
	public void selectStatusOption(String optionText) throws InterruptedException {
		waitForScreenToLoad("Admin");
		WebElement statusDropdown = dropdowns.get(1);

		statusDropdown.click();
		selectDropdownOption(optionText);
	}
	
	@FindBy(css = "div.oxd-autocomplete-dropdown")
	private WebElement autocompleteDropdown;
	
	public void typeAndSelectEmployeeName(String employeeName) {
		employeeNameField.click();
		employeeNameField.sendKeys(employeeName);
		new WebDriverWait(driver, Duration.ofSeconds(5))
	    .until(ExpectedConditions.visibilityOf(autocompleteDropdown));
		
		new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(ExpectedConditions.not(ExpectedConditions
        		.textToBePresentInElement(autocompleteDropdown, "Searching....")));
		
		Assert.assertEquals(autocompleteDropdown.getText(), employeeName);
		autocompleteDropdown.click();
		//employeeNameField.sendKeys(Keys.ARROW_DOWN);
		//employeeNameField.sendKeys(Keys.ENTER);
	}
	
	public void enterUserCredentials(String username, String password) {
	    WebElement usernameField = inputFieldsList.get(1);
	    WebElement passwordField = inputFieldsList.get(2);
		WebElement confirmPasswordField = inputFieldsList.get(3);
		
		usernameField.sendKeys(username);
		passwordField.sendKeys(password);
		confirmPasswordField.sendKeys(password);
	}
	
	
	public void submitUserAdd() {
		submitLoginButton.click();
	}
}
