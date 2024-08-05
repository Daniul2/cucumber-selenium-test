import core.Waiter;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestBase {

    protected static WebDriver driver;
    protected Waiter waiter;
    private String userMail = "xecid76597@leacore.com";
    private String userPass = "Test123Test!";

    TestBase() {
        this.driver = new ChromeDriver();
        this.waiter = new Waiter(driver);
    }

    public String getUserMail() {
        return userMail;
    }

    public String getUserPass() {
        return userPass;
    }

    public static WebDriver getDriver() {
        return driver;
    }
}