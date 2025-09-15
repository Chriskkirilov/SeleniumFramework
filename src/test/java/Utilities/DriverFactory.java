package Utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class DriverFactory {
    private DriverFactory() {}

    public static WebDriver create() {
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless=new");
        opts.addArguments("--no-sandbox");
        opts.addArguments("--disable-dev-shm-usage");
        opts.addArguments("start-maximized");
        return new ChromeDriver(opts);
    }
}
