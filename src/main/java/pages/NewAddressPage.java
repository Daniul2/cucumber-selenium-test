package pages;

import core.BasePage;
import core.Waiter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.Map;

import static org.openqa.selenium.By.*;

public class NewAddressPage extends BasePage {

    @FindBy(css = "body#address")
    protected WebElement root;

    @FindBy(css = "input#field-alias")
    protected WebElement aliasInput;

    @FindBy(css = "input#field-firstname")
    protected WebElement firstNameInput;

    @FindBy(css = "input#field-lastname")
    protected WebElement lastNameInput;

    @FindBy(css = "input#field-company")
    protected WebElement companyInput;

    @FindBy(css = "input#field-vat_umber")
    protected WebElement vatNumberInput;

    @FindBy(css = "input#field-address1")
    protected WebElement address_1Input;

    @FindBy(css = "input#field-city")
    protected WebElement cityInput;

    @FindBy(css = "input#field-postcode")
    protected WebElement postCodeInput;

    @FindBy(css = "select#field-id_country")
    protected WebElement countrySelect;

    @FindBy(css = "input#field-phone")
    protected WebElement phoneInput;

    @FindBy(css = "button[type='submit']")
    protected WebElement submitAddressBtn;

    @Override
    public WebElement getRoot() {
        return root;
    }

    public NewAddressPage(WebDriver driver, Waiter waiter) {
        super(driver, waiter);
    }

    public AddressesPage setNewAddress(Map<String, String> addressData) {
        addressData.forEach((field, value) -> {
            field = field.toLowerCase();
            switch(field) {
                case "address":
                    field = "address1";
                case "address complement":
                    field = "address2";
            }

            if (field.equals("country")) {
                countrySelect = waiter.forElementSafelyClicked(countrySelect);
                Select countryDropdown = new Select(countrySelect);
                countryDropdown.selectByValue(value);
            } else {
                waiter.forElementSafelyClicked(cssSelector("input[name='" + field.toLowerCase() + "']"))
                        .sendKeys(value);
            }

        });
        waiter.forElementSafelyClicked(submitAddressBtn);
        return new AddressesPage(driver, waiter);
    }


}
