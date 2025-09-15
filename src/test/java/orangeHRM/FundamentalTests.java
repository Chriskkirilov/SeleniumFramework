package orangeHRM;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import PageObjectClasses.AdminPage;
import PageObjectClasses.DashboardPage;
import PageObjectClasses.DashboardPage.SideMenuOptions;

import PageObjectClasses.LoginPage;
import PageObjectClasses.PIMAddEmployeePage;
import PageObjectClasses.PIMEmployeeListPage;
import Utilities.DriverFactory;
import PageObjectClasses.PIMAddEmployeePage.UserStatus;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FundamentalTests {
	WebDriver driver;

	@BeforeMethod
	public void setUp() throws InterruptedException {
		driver = DriverFactory.create();
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
	    String uniqueUsername = "AA_" + shortUuid();
	    String userID = "id_" + shortUuid();

	    DashboardPage dashboardPage = new DashboardPage(driver);

	    dashboardPage.clickOnSideOption(SideMenuOptions.PIM);

	    PIMEmployeeListPage pimPage = new PIMEmployeeListPage(driver);
	    pimPage.clickAddButton();

	    PIMAddEmployeePage pimAddEmployeePage = new PIMAddEmployeePage(driver);
	    pimAddEmployeePage.fillAddEmployeeForm(uniqueFirstName, "adam", "john", userID);
	    pimAddEmployeePage.createLoginDetails(uniqueUsername, "williams39943", UserStatus.Disabled);
	    pimAddEmployeePage.submitAddUser();

	    //pimAddEmployeePage.waitForSuccessToast();
	    Thread.sleep(4000);

	    By adminSideButtonBy = By.xpath("(//ul[@class='oxd-main-menu'])//li[1]");
	    new Utilities.Waits(driver).clickable(adminSideButtonBy);
	    driver.findElement(adminSideButtonBy).click();

	    AdminPage adminPage = new AdminPage(driver);
	    adminPage.clickAddButton();
	    dashboardPage.waitForScreenToLoad();
	    adminPage.selectUserRoleOption("ESS");
	    adminPage.selectStatusOption("Disabled");

	    adminPage.typeAndSelectEmployeeName(uniqueFirstName + " adam john");
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