
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import pages.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.*;
import static pages.parameters.TestParameters.*;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class TaskTwoSteps extends TestBaseJUnit {

    protected HomePage homePage;
    protected AccountPage accPage;
    protected ProductPage productPage;
    protected CartPage cartPage;
    protected CheckoutPage checkoutPage;
    protected Map<String, String> dataAddress;
    protected final String EMAIL = getUserMail();
    protected final String PASS = getUserPass();
    protected final String PRODUCT_NAME = "Hummingbird Printed Sweater";

    @Test
    @Order(1)
    @DisplayName("Logs in to the same user from task")
    public void logs_in_to_the_same_user_from_task() {
        this.homePage = new HomePage(driver, waiter);
        this.accPage = homePage.openHomePage()
                .goToLoginPage()
                .logIntoUser(EMAIL, PASS);
    }

    @Test
    @Order(2)
    @DisplayName("Chooses the Hummingbird Printed Sweater for purchase")
    public void chooses_the_hummingbird_printed_sweater_for_purchase() {
        homePage.openHomePage();
        final var choosenProduct = homePage.getProductByName(PRODUCT_NAME);
        waiter.forElementSafelyClicked(choosenProduct);
        this.productPage = new ProductPage(driver, waiter);
    }

    @Test
    @Order(3)
    @DisplayName("checks if the discount on it is 20%")
    public void checks_if_the_discount_on_it_is() {
        int discountPercentage = 20;
        final var isDiscountCorrect = productPage.verifyDiscount(discountPercentage);
        final var actualDiscount = productPage.getProductDiscount();
        assertThat(isDiscountCorrect)
                .as("Actual discount didn't match with expected! Expected: " + discountPercentage + "%, actual: " + actualDiscount + "!")
                .isTrue();
    }

    @Test
    @Order(4)
    @DisplayName("Selects size S")
    public void selects_size_s() {
        // S size is unavailable for purchase on website
        productPage.setSize(ClothingSizes.M);
    }

    @Test
    @Order(5)
    @DisplayName("Selects {int} pieces according to the parameter given in the test")
    public void selects_pieces_according_to_the_parameter_given_in_the_test() {
        productPage.setQuantity(5);
    }

    @Test
    @Order(6)
    @DisplayName("Adds the product to the cart,")
    public void adds_the_product_to_the_cart() {
        productPage.addToCart();
    }

    @Test
    @Order(7)
    @DisplayName("Proceeds to the checkout option,")
    public void proceeds_to_the_checkout_option() {
        this.cartPage = productPage.proceedToCart();
        this.checkoutPage = cartPage.proceedToCheckout();
    }

    @Test
    @Order(8)
    @DisplayName("Confirms the address,")
    public void confirms_the_address() {
        this.dataAddress = Map.of("Address1", "1",
                "City", "1",
                "Postcode", "1",
                "Phone", "1");
        checkoutPage.confirmAddress(dataAddress.values());
    }


    @Test
    @Order(9)
    @DisplayName("Selects the delivery method - PrestaShop 'pick up in store'")
    public void selects_the_delivery_method_presta_shop() {
        checkoutPage.selectDeliveryMethod(DeliveryOptions.SELF_PICKUP);
    }

    @Test
    @Order(10)
    @DisplayName("Selects the payment option - Pay by Check")
    public void selects_the_payment_option_pay_by_check() {
        checkoutPage.selectPaymentMethod(PaymentOptions.BY_CHECK);
    }

    @Test
    @Order(11)
    @DisplayName("Clicks on 'order with an obligation to pay'")
    public void clicks_on() {
        // There is no such option when selecting Pay By Check
    }

    @Test
    @Order(12)
    @DisplayName("Takes a screenshot with the order confirmation and the amount.")
    public void takes_a_screenshot_with_the_order_confirmation_and_the_amount() {
        super.captureScreenshot("orderConfirmation");
    }
}
