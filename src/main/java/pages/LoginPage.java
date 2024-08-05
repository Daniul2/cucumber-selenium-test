package pages;

import core.BasePage;
import core.Waiter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    @FindBy(css = "body#authentication")
    protected WebElement root;

    @FindBy(css = "input#field-email")
    protected WebElement emailInput;

    @FindBy(css = "input#field-password")
    protected WebElement passInput;

    @FindBy(css = "button#submit-login")
    protected WebElement signInBtn;

    public WebElement getRoot() {
        return root;
    }

    public LoginPage(WebDriver driver, Waiter waiter) {
        super(driver, waiter);
    }

    public AccountPage logIntoUser(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
        waiter.waitForLoad();
        return new AccountPage(driver, waiter);
    }

    public void enterEmail(String email) {
        waiter.forElementSafelyClicked(emailInput).sendKeys(email);
    }

    public void enterPassword(String password) {
        waiter.forElementSafelyClicked(passInput).sendKeys(password);
    }

    public void clickSignIn() {
        waiter.forElementSafelyClicked(signInBtn);
    }


}
