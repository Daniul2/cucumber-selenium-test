package pages;

import core.BasePage;
import core.Waiter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {

    @FindBy(css = "div#cart")
    protected WebElement root;

    @FindBy(css = "div.checkout a")
    protected WebElement proceedToCheckoutBtn;

    public CartPage(WebDriver driver, Waiter waiter) {
        super(driver, waiter);
    }

    @Override
    public WebElement getRoot() {
        return root;
    }

    public CheckoutPage proceedToCheckout() {
        waiter.forElementSafelyClicked(proceedToCheckoutBtn);
        return new CheckoutPage(driver, waiter);
    }
}
