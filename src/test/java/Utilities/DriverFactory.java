package Utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class DriverFactory {
    private DriverFactory() {}
    public static WebDriver create() {
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless=new", "--window-size=1440,900",
                          "--no-sandbox", "--disable-dev-shm-usage");
        return new ChromeDriver(opts);
    }
}