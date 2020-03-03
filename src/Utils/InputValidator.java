package Utils;

public class InputValidator {
    //input validation
    public static void validateCustomerInput(String customerName, String phone, String addressLine1, String addressLine2, String cityName, String postalCode, String countryName) throws IllegalArgumentException {
        if (customerName.equals("") || phone.equals("") || addressLine1.equals("") || addressLine2.equals("") || cityName.equals("") || postalCode.equals("") || countryName.equals("")) {
            throw new IllegalArgumentException("The data you have entered is invalid or incomplete.");
        }
    }
    //validate appointment scheduling to not occur in off hours
    //validate no overlapping appointments
    //invalid login text
    public static boolean invalidLoginText(String text) {
        return text == null || text.equals("") || text.contains("'") || text.contains("\"");
    }
}
