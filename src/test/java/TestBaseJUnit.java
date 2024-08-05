import core.Waiter;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;

public class TestBaseJUnit {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    protected static WebDriver driver;
    protected static Waiter waiter;
    private String userMail = "xecid76597@leacore.com";
    private String userPass = "Test123Test!";

    @BeforeAll
    public static void init() {
        ChromeOptions ch = new ChromeOptions();
        ch.addArguments("--disable-search-engine-choice-screen");
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driverThreadLocal.set(new ChromeDriver(ch));
        driver = getDriver();
        waiter = new Waiter(driver);
    }

    @AfterAll
    public static void shutdown() {
        driver.quit();
    }

    public String getUserMail() {
        return userMail;
    }

    public String getUserPass() {
        return userPass;
    }

    public static WebDriver getDriver(){
        return driverThreadLocal.get();
    }

    public void captureScreenshot(String screenshotName) {
        try {
            driver.manage().window().maximize();
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File source = screenshot.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File("./src/test/resources/" + screenshotName + ".png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
