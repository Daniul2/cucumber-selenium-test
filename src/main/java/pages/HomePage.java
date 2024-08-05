package pages;

import core.BasePage;
import core.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.openqa.selenium.By.*;

public class HomePage extends BasePage {
    @FindBy(css = "body#index")
    protected WebElement root;

    @FindBy(css = "[title='Log in to your customer account']")
    protected WebElement loginBtn;

    @FindBy(css = ".products.row")
    protected WebElement productsSection;

    public WebElement getRoot() {
        return root;
    }

    public HomePage(WebDriver driver, Waiter waiter) {
        super(driver, waiter);
    }

    public HomePage openHomePage() {
        driver.get("https://mystore-testlab.coderslab.pl/");
        waitForLoad();
        return this;
    }

    public LoginPage goToLoginPage() {
        waiter.forElementSafelyClicked(loginBtn);
        waiter.waitForLoad();
        return new LoginPage(driver, waiter);
    }

    public WebElement getProductByName(String name) {
        final var getAllProductTiles = waiter.forAllChildElementsPresent(root, cssSelector("article.product-miniature"));
        for(WebElement product : getAllProductTiles) {
            final var productName = waiter.forChildElementVisibleBy(product, cssSelector("h3.product-title")).getText().toLowerCase();
            if(productName.equals(name.toLowerCase())) {
                return product;
            }
        }

        return null;
    }

    public List<WebElement> getAllProducts() {
        return waiter.forAllChildElementsPresent(root, cssSelector("article.product-miniature"));
    }
}
