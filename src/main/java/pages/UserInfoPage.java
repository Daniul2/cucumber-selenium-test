package pages;

import core.BasePage;
import core.Waiter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UserInfoPage extends BasePage {
    public UserInfoPage(WebDriver driver, Waiter waiter) {
        super(driver, waiter);
    }

    @Override
    public WebElement getRoot() {
        return root;
    }
}
