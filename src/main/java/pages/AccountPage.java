package pages;

import core.BasePage;
import core.Waiter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountPage extends BasePage {

    @FindBy(css = "body#my-account")
    protected WebElement root;

    @FindBy(css = "a#identity-link")
    protected WebElement informationSection;

    @FindBy(css = "a#addresses-link")
    protected WebElement addressesLink;

    @FindBy(css = "a#history-link")
    protected WebElement ordersHistorySection;

    @FindBy(css = "a#order-slips-link")
    protected WebElement creditSlipsSection;

    @FindBy(css = "a#wishlist-link")
    protected WebElement wishList;

    public AccountPage(WebDriver driver, Waiter waiter) {
        super(driver, waiter);
    }

    @Override
    public WebElement getRoot() {
        return root;
    }

    public <T extends BasePage> T openSection(String sectionName, Class<T> type) {
        sectionName = sectionName.toLowerCase();
        switch(sectionName) {
            case "addresses":
                waiter.forElementSafelyClicked(addressesLink);
                waiter.waitForLoad();
                return type.cast(new AddressesPage(driver, waiter));
            case "information":
                waiter.forElementSafelyClicked(informationSection);
                waiter.waitForLoad();
                return type.cast(new UserInfoPage(driver, waiter));
        }

        return type.cast(this);
    }
}
