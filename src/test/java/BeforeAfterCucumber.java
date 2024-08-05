import io.cucumber.java.AfterAll;

public class BeforeAfterCucumber {

    @AfterAll
    public static void before_or_after_all() {
        TestBase.getDriver().quit();
    }
}
