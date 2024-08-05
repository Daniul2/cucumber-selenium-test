package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {

    protected WebElement root;
    protected Waiter waiter;
    protected WebDriver driver;

    public BasePage(WebDriver driver, Waiter waiter) {
        this.driver = driver;
        this.waiter = waiter;
        PageFactory.initElements(driver, this);
    }

    public abstract WebElement getRoot();

    public void waitForLoad() {
        waiter.waitForLoad();
    }

}
