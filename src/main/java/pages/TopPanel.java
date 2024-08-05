package pages;

import core.BasePage;
import core.Waiter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TopPanel extends BasePage {


    @FindBy(css = "header#header")
    protected WebElement root;

    @FindBy(css = "div.top-logo a")
    protected WebElement pageLogo;

    @Override
    public WebElement getRoot() {
        return root;
    }

    public TopPanel(WebDriver driver, Waiter waiter) {
        super(driver, waiter);
        PageFactory.initElements(driver, this);
    }
}
