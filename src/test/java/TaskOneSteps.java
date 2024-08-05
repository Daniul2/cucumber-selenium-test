import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AccountPage;
import pages.AddressesPage;
import pages.HomePage;
import pages.NewAddressPage;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class TaskOneSteps extends TestBase {
    protected HomePage homePage;
    protected AccountPage accPage;
    protected AddressesPage addressesPage;
    protected NewAddressPage newAddressPage;
    protected final String EMAIL = getUserMail();
    protected final String PASS = getUserPass();
    protected Map<String, String> data;

    @Given("I'm on https:\\/\\/mystore-testlab.coderslab.pl\\/ logined as new user")
    public void loginAsCreatedUser() {
        this.homePage = new HomePage(driver, waiter);
        this.accPage = homePage.openHomePage()
                    .goToLoginPage()
                    .logIntoUser(EMAIL, PASS);
    }

    @When("Clicks on the {string} tile after logging in")
    public void navigateToAddressPage(String section) {
        this.addressesPage = accPage.openSection(section, AddressesPage.class);
        addressesPage.waitForLoad();
    }

    @Then("The address we should be at: {string})")
    public void checkThatAddressIsValid(String expectedUrl) {
        final var url = driver.getCurrentUrl();
        assertThat(url)
                .as("Actual url doesn't match with expected!")
                .isEqualTo(expectedUrl);
    }

    @When("Clicks on + Create new address,")
    public void clickOnCreateNewAddress() {
        this.newAddressPage = addressesPage.clickCreateNewAddress();
        newAddressPage.waitForLoad();
    }

    @When("Fills out the New address form with {string}, {string}, {string}, {string}, {string}, {string}")
    public void fills_out_the_new_address_form_with(String alias, String address, String city, String zipPostalCode, String country, String phone) {
        data = Map.of("Alias", alias,
                "Address1", address,
                "City", city,
                "Postcode", zipPostalCode,
                "Phone", phone);
        this.addressesPage = newAddressPage.setNewAddress(data);
    }

    @Then("Checks if the data in the added address is correct..")
    public void checks_if_the_data_in_the_added_address_is_correct() {
        addressesPage.checkIfSuccessAlertIsPresent();
        final var allAddresses = addressesPage.getAllAddresses();
        assertThat(allAddresses)
                .as("There is no added data in address tiles!")
                .anyMatch(address -> address.containsAll(data.values()));
    }

    @When("Deletes the above address by clicking Delete")
    public void deletes_the_above_address_by_clicking_delete() {
        addressesPage.clickDeleteAddress("Kirich");
    }

    @Then("Checks if the address has been deleted.")
    public void checks_if_the_address_has_been_deleted() {
        final var allAddress = addressesPage.getAllAddresses();
        assertThat(allAddress)
                .as("Added address is still on the address page!")
                .noneMatch(address -> address.containsAll(data.values()));
    }


}
