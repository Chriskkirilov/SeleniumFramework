package PageObjectClasses;

import static org.testng.Assert.assertEquals;

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

	private static final int COL_USERNAME = 2;
	private static final int COL_USER_ROLE = 3;
	private static final int COL_EMPLOYEE_NAME = 4;
	private static final int COL_STATUS = 5;

	WebDriver driver;

	public AdminPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='orangehrm-header-container']//button")
	WebElement addButton;

	@FindBy(xpath = "//div[@class='oxd-select-text oxd-select-text--active']")
	List<WebElement> dropdowns;

	@FindBy(css = "div.oxd-select-dropdown")
	WebElement dropdownWrapper;

	@FindBy(xpath = "//div[@class='oxd-autocomplete-wrapper']//div//input")
	WebElement employeeNameField;

	@FindBy(css = "button[type='submit']")
	WebElement submitLoginButton;
	
	@FindBy(className = ".oxd-icon.bi-check.oxd-checkbox-input-icon")
	WebElement changePassToggle;

	@FindBy(css = "input.oxd-input")
	List<WebElement> inputFieldsList;
	
	@FindBy(xpath = "//input[@type='password']")
	List<WebElement> passwordFields;


	 public void clickAddButton() {
	        waits.clickable(addButton).click();
	}

	public void waitForAdminFormToAppear() {
		waitForElementToBecomeVisible(By.className("orangehrm-card-container"));
	}

	public void selectDropdownOption(String optionText) {
		List<WebElement> options = dropdownWrapper
				.findElements(By.cssSelector("div.oxd-select-dropdown div.oxd-select-option"));
		options.stream().filter(option -> option.getText().trim().equalsIgnoreCase(optionText)).findFirst()
				.orElseThrow(() -> new RuntimeException("Option not found: " + optionText)).click();

	}

	@FindBy(css = "div.oxd-autocomplete-dropdown")
	private WebElement autocompleteDropdown;

	public void typeAndSelectEmployeeName(String employeeName) {
	    employeeNameField.click();
	    employeeNameField.sendKeys(employeeName);

	    waits.visible(autocompleteDropdown);
	    waits.textNotPresentIn(autocompleteDropdown, "Searching....");

	    autocompleteDropdown.click();
	}

	public void selectUserRoleOption(String optionText) {
	    waitForScreenToLoad("Admin");
	    WebElement userRoleDropdown = dropdowns.get(0);
	    waits.clickable(userRoleDropdown).click();
	    selectDropdownOption(optionText);
	}

	public void selectStatusOption(String optionText) {
	    waitForScreenToLoad("Admin");
	    WebElement statusDropdown = dropdowns.get(1);
	    waits.clickable(statusDropdown).click();
	    selectDropdownOption(optionText);
	}

	public void enterUsername(String username) throws InterruptedException {
		WebElement usernameField = inputFieldsList.get(1);
		Thread.sleep(1000);

		usernameField.sendKeys(username);
	}
	
	public void enterUserPassword(String password) throws InterruptedException {
		WebElement passwordField = inputFieldsList.get(2);
		WebElement confirmPasswordField = inputFieldsList.get(3);

		passwordField.sendKeys(password);
		Thread.sleep(1000);
		confirmPasswordField.sendKeys(password);
		Thread.sleep(1000);
	}
	
	public void enterUserCredentials(String username, String password) throws InterruptedException {
		enterUsername(username);
		enterUserPassword(password);
	}

	public void submitUserAdd() {
        waits.clickable(submitLoginButton).click();
	}

	private static List<WebElement> getRowCells(WebDriver driver, String usernameKeyInRow) throws InterruptedException {
		Thread.sleep(3000);
		String rowXpath = "//div[text() = '" + usernameKeyInRow + "']"
				+ "//parent::div//parent::div//parent::div//div[@class='oxd-table-cell oxd-padding-cell']";

		WebElement row = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(rowXpath)));

		return driver.findElements(By.xpath(rowXpath));
	}
	
	
	public void clickEditOnRow(String username) throws InterruptedException {
		System.out.println(getRowCells(driver, username).size());
		getRowCells(driver, username).getLast().findElements(By.tagName("button")).getLast().click();
	}
	public void editUserRow(String userRole, String status, String username, String newPassword) throws InterruptedException {
		selectUserRoleOption(userRole);
		selectStatusOption(status);
		enterUsername(username);
	    if (newPassword != null && !newPassword.isEmpty()) {
	    	changePassToggle.click();
	    	passwordFields.get(0).sendKeys(newPassword);
	    	passwordFields.get(1).sendKeys(newPassword);

	    }
	    submitUserAdd();
	}

	public void assertUserRow(WebDriver driver, String expectedUsername, String expectedUserRole,
			String expectedEmployeeName, String expectedStatus) throws InterruptedException {

		List<WebElement> cells = getRowCells(driver, expectedUsername);
		if (expectedUsername != null)
			assertEquals(cells.get(1).getText(), expectedUsername, "Username");
		if (expectedUserRole != null)
			assertEquals(cells.get(2).getText(), expectedUserRole, "User Role");
		if (expectedEmployeeName != null)
			assertEquals(cells.get(3).getText(), expectedEmployeeName, "Employee Name");
		if (expectedStatus != null)
			assertEquals(cells.get(4).getText(), expectedStatus, "Status");
	}
}
