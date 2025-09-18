package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTestCase {
	public WebDriver driver;
	
	public void InitializeDriver() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//src//test//java//resources//GlobalData.properties");
		prop.load(fis);
		
		String browserName = prop.getProperty("browser");
		if (browserName.equalsIgnoreCase("chrome")) {
		ChromeOptions opts = new ChromeOptions();
		opts.addArguments("--headless=new");
		opts.addArguments("--no-sandbox");
		opts.addArguments("--disable-dev-shm-usage");
		opts.addArguments("start-maximized");
		driver = new ChromeDriver();
		} //todo - add other browsers
	}
	
//	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
//		TakesScreenshot ts = (TakesScreenshot)driver;
//		File source = ts.getScreenshotAs(OutputType.FILE);
//		File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
//		FileUtils.copyFile(source, file);
//		
//		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
//		
//	}

}