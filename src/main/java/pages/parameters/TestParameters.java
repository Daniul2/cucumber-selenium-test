package pages.parameters;

public class TestParameters {

    public enum ClothingSizes {
        S("S"),
        M("M"),
        L("L"),
        XL("XL");

        public final String val;

        private ClothingSizes(String val) {
            this.val = val;
        }
    }

    public enum DeliveryOptions {
        SELF_PICKUP("Self pick up"),
        MY_CARRIER("My carrier");

        public final String val;

        private DeliveryOptions(String val) {
            this.val = val;
        }
    }

    public enum PaymentOptions {
        BY_CHECK("Pay by Check"),
        BY_BANK_WIRE("Pay by bank wire");

        public final String val;

        private PaymentOptions(String val) {
            this.val = val;
        }
    }

}
