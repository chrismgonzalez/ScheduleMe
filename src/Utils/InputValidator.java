package Utils;

public class InputValidator {
    //input validation
    public static void validateCustomerInput(String customerName, String phone, String addressLine1, String addressLine2, String cityName, String postalCode, String countryName) throws IllegalArgumentException {
        if (customerName.equals("") || phone.equals("") || addressLine1.equals("") || addressLine2.equals("") || cityName.equals("") || postalCode.equals("") || countryName.equals("")) {
            throw new IllegalArgumentException("The data you have entered is invalid or incomplete.");
        }
    }

    public static String validateHourInput(String hour) throws IllegalArgumentException {
        String validHourString = hour.length() == 1 ? "0" + hour : hour;
        try {
            int hourInt = Integer.parseInt(validHourString);
            if (hourInt < 1 || hourInt > 12) {
                throw new IllegalArgumentException();
            }
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Appointment start or end time is invalid!");
        }
        return validHourString;
    }

    public static String validateMinuteInput(String minute) throws IllegalArgumentException {
        String validMinuteString = minute;
        validMinuteString = validMinuteString.length() == 0 ? "00" : validMinuteString;
        validMinuteString = validMinuteString.length() == 1 ? "0" + validMinuteString : validMinuteString;
        try {
            int minuteInt = Integer.parseInt(validMinuteString);
            if (minuteInt < 0 || minuteInt >= 60) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Appointment start or end time is invalid.");
        }
        return validMinuteString;
    }



    //validate no overlapping appointments
    //invalid login text
    public static boolean invalidLoginText(String text) {
        return text == null || text.equals("") || text.contains("'") || text.contains("\"");
    }
}
