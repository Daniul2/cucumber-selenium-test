package pages;

import core.BasePage;
import core.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.parameters.TestParameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.openqa.selenium.By.*;
import static pages.parameters.TestParameters.*;

public class CheckoutPage extends BasePage {
    public CheckoutPage(WebDriver driver, Waiter waiter) {
        super(driver, waiter);
    }

    @FindBy(css = "body#checkout")
    protected WebElement root;

    @FindBy(css = "button[name='confirm-addresses']")
    protected WebElement confirmAddressBtn;

    @FindBy(css = "div#delivery-addresses")
    protected WebElement deliveryAddressesSection;

    @FindBy(css = "section#checkout-payment-step")
    protected WebElement paymentOptionsSection;

    @FindBy(css = "button[name='confirmDeliveryOption']")
    protected WebElement shippingConfirmBtn;

    @Override
    public WebElement getRoot() {
        return root;
    }

    public void selectDeliveryMethod(DeliveryOptions deliveryOptions) {
        waiter.forChildElementSafelyClicked(root, xpath(".//span[normalize-space()='" + deliveryOptions.val + "']/ancestor::div[contains(@class, 'row delivery-option')]"));
        waiter.forElementSafelyClicked(shippingConfirmBtn);
    }

    public void confirmAddress(Collection<String> values) {
        final var allAddresses = getAllAddressTiles();
        allAddresses.forEach(tile -> {
            final var addressText = Arrays.stream(tile.findElement(cssSelector(".address"))
                            .getText()
                            .split("\\n")).toList();
            if (addressText.containsAll(values)) {
                final var classValue = tile.getAttribute("class");
                if (!classValue.contains("selected")) {
                    waiter.forElementSafelyClicked(tile);
                }
            }
        });

        waiter.forElementSafelyClicked(confirmAddressBtn);
    }

    public List<WebElement> getAllAddressTiles() {
        return waiter.forAllChildElementsPresent(deliveryAddressesSection, cssSelector("article.address-item"))
                .stream()
                .toList();
    }

    public void selectPaymentMethod(PaymentOptions paymentOptions) {
        waiter.forChildElementSafelyClicked(paymentOptionsSection, xpath(".//label[contains(@for, 'payment-option')]/span[contains(normalize-space(), '" + paymentOptions.val + "')]/.."));
        waiter.forChildElementVisibleBy(paymentOptionsSection, cssSelector("form#conditions-to-approve li")).click();
        waiter.forChildElementSafelyClicked(paymentOptionsSection, cssSelector("div#payment-confirmation button:not(disabled)"));
    }
}
