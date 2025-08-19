package testBooking;

import org.testng.annotations.Test;

import PageObjectClasses.AdminPage;
import PageObjectClasses.DashboardPage;
import PageObjectClasses.DashboardPage.SideMenuOptions;

import PageObjectClasses.LoginPage;
import PageObjectClasses.PIMAddEmployeePage;
import PageObjectClasses.PIMEmployeeListPage;
import PageObjectClasses.PIMAddEmployeePage.UserStatus;


import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class sampleTestBooking {
	@Test
    public void test1() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        Thread.sleep(4000);
        
        LoginPage loginPage = new LoginPage(driver);
        
        String[] loginCredentials = loginPage.extractLoginCredentialsFromString();
        loginPage.fillLoginForm(loginCredentials[0], loginCredentials[1]);
        
        loginPage.submitLogin();
        
        DashboardPage dashboardPage = new DashboardPage(driver);

        dashboardPage.clickOnSideOption(SideMenuOptions.PIM);
        
        PIMEmployeeListPage pimPage = new PIMEmployeeListPage(driver);
        pimPage.clickAddButton();
        
        PIMAddEmployeePage pimAddEmployeePage = new PIMAddEmployeePage(driver);
        pimAddEmployeePage.fillAddEmployeeForm("rosho1444", "peter", "ivan", "rosho1163");
        pimAddEmployeePage.createLoginDetails("rosho1163", "williams39943", UserStatus.Enabled);
        pimAddEmployeePage.submitAddUser();
        wait.until(d -> (driver.findElement(By.cssSelector(".oxd-toast.oxd-toast--success.oxd-toast-container--toast")).isDisplayed()));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".oxd-toast.oxd-toast--success.oxd-toast-container--toast")));

        Thread.sleep(4000);
        
        WebElement adminSideButton = driver.findElement(By.xpath("(//ul[@class='oxd-main-menu']) //li[1]"));
        adminSideButton.click();
        

//        wait.until(d -> (driver.findElement(By.xpath("//div[@class='orangehrm-header-container']//button")).isDisplayed()));
//        pimPage.clickAddButton();
//        dashboardPage.waitForScreenToLoad();
//        AdminPage adminPage = new AdminPage(driver);
//        adminPage.selectUserRoleOption("Admin");
//        adminPage.selectStatusOption("Enabled");
//        
//        adminPage.typeAndSelectEmployeeName("gosho141 peter ivan");
//        adminPage.enterUserCredentials("gosho9963", "williams39943");
//        adminPage.submitUserAdd();
//        

//        List<WebElement> fields = driver.findElements(By.xpath("(//input[@class='oxd-input oxd-input--active'])"));
//        fields.get(1).sendKeys("lastEmployeeName");
//        fields.get(2).sendKeys("aA234567");
//        fields.get(3).sendKeys("aA234567");
//        
//        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
    }
}
