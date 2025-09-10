package orangeHRM;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import PageObjectClasses.AdminPage;
import PageObjectClasses.DashboardPage;
import PageObjectClasses.DashboardPage.SideMenuOptions;

import PageObjectClasses.LoginPage;
import PageObjectClasses.PIMAddEmployeePage;
import PageObjectClasses.PIMEmployeeListPage;
import Utilities.DriverFactory;
import PageObjectClasses.PIMAddEmployeePage.UserStatus;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FundamentalTests {
    private WebDriver driver = new ChromeDriver();

	@BeforeTest
	public void setUp() throws InterruptedException {
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

		Thread.sleep(4000);
		LoginPage loginPage = new LoginPage(driver);
		String[] loginCredentials = loginPage.extractLoginCredentialsFromString();
		loginPage.fillLoginForm(loginCredentials[0], loginCredentials[1]);

		loginPage.submitLogin();
	}
	

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
	
	@Test
	public void loginAddUserAndEmployee() throws InterruptedException {
	    String uniqueFirstName = "name_" + shortUuid();
	    String uniqueUsername = "A_" + shortUuid();
	    String userID = "id_" + shortUuid();

	    DashboardPage dashboardPage = new DashboardPage(driver);

	    dashboardPage.clickOnSideOption(SideMenuOptions.PIM);

	    PIMEmployeeListPage pimPage = new PIMEmployeeListPage(driver);
	    pimPage.clickAddButton();

	    PIMAddEmployeePage pimAddEmployeePage = new PIMAddEmployeePage(driver);
	    pimAddEmployeePage.fillAddEmployeeForm(uniqueFirstName, "peter", "john", userID);
	    pimAddEmployeePage.createLoginDetails(uniqueUsername, "williams39943", UserStatus.Disabled);
	    pimAddEmployeePage.submitAddUser();

	    pimAddEmployeePage.waitForSuccessToast();

	    By adminSideButtonBy = By.xpath("(//ul[@class='oxd-main-menu'])//li[1]");
	    new Utilities.Waits(driver).clickable(adminSideButtonBy);
	    driver.findElement(adminSideButtonBy).click();

	    AdminPage adminPage = new AdminPage(driver);
	    adminPage.clickAddButton();
	    dashboardPage.waitForScreenToLoad();
	    adminPage.selectUserRoleOption("ESS");
	    adminPage.selectStatusOption("Disabled");

	    adminPage.typeAndSelectEmployeeName(uniqueFirstName + " peter john");
	    adminPage.enterUserCredentials(uniqueUsername, "williams39943");
	    adminPage.submitUserAdd();

	    dashboardPage.clickOnSideOption(SideMenuOptions.Admin);

	    adminPage.assertUserRow(driver, uniqueUsername, "ESS", uniqueFirstName + " john", "Disabled");
	    
	    adminPage.clickEditOnRow(uniqueUsername);
	}

	public static String shortUuid() {
		return java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 6);
	}
}