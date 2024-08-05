package pages;

import core.BasePage;
import core.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.openqa.selenium.By.*;

public class AddressesPage extends BasePage {

    protected final String ADDRESS_TILE_CSS = "article.address";
    protected final String ADDRESS_DELETE_BTN = "[data-link-action='delete-address']";
    protected final String ADDRESS_UPDATE_BTN = "[data-link-action='edit-address']";


    @FindBy(css = "body#addresses")
    protected WebElement root;

    @FindBy(css = "a[data-link-action='add-address']")
    protected WebElement createNewAddressBtn;

    @FindBy(css = "article[data-alert='success']")
    protected WebElement successAlert;

    @FindAll({
            @FindBy(css = ADDRESS_TILE_CSS)
    })
    protected List<WebElement> addressTiles;

    public AddressesPage(WebDriver driver, Waiter waiter) {
        super(driver, waiter);
    }

    public WebElement getRoot() {
        return root;
    }

    public void checkIfSuccessAlertIsPresent() {
        try {
            waiter.forElementVisible(Duration.ofSeconds(10), successAlert);
        } catch(TimeoutException e) {
            throw new AssertionError("There is no success message on the page!");
        }
    }

    public List<List<String>> getAllAddresses() {
        return addressTiles.stream()
                .map(tile -> Arrays.stream(
                        tile.findElement(cssSelector(".address-body"))
                                .getText()
                                .split("\\n")).toList())
                .collect(Collectors.toList());
    }

    public NewAddressPage clickUpdateAddress(String keyword) {
        final var allAddressTiles = addressTiles;
        for(WebElement address : allAddressTiles) {
            if(address.getText().contains(keyword)) {
                waiter.forChildElementSafelyClicked(address, cssSelector(ADDRESS_UPDATE_BTN));
                break;
            }
        }

        return new NewAddressPage(driver, waiter);
    }

    public AddressesPage clickDeleteAddress(String keyword) {
        final var allAddressTiles = addressTiles;
        for(WebElement address : allAddressTiles) {
            if(address.getText().contains(keyword)) {
                waiter.forChildElementSafelyClicked(address, cssSelector(ADDRESS_DELETE_BTN));
                checkIfSuccessAlertIsPresent();
                break;
            }
        }

        return this;
    }



    public NewAddressPage clickCreateNewAddress() {
        waiter.forElementSafelyClicked(createNewAddressBtn);
        waiter.waitForLoad();
        return new NewAddressPage(driver, waiter);
    }
}
