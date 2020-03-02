package Resources;

import java.util.ListResourceBundle;

public class Login_en extends ListResourceBundle {
    protected Object[][] getContents() {
        return new Object[][] {
                { "username", "Username" },
                { "password", "Password"},
                { "error_invalid", "Invalid username or password"},
                { "close", "close"}
        };
    }
}
