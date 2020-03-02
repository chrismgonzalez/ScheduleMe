package Resources;

import java.util.ListResourceBundle;

public class Login_de extends ListResourceBundle {
    protected Object[][] getContents() {
        return new Object[][] {
                { "username", "Nutzername" },
                { "password", "Passwort"},
                { "login", "Anmeldung"},
                { "error_invalid", "Ungültiger Benutzername oder Passwort"},
                { "close", "Schließen"},
                { "loginMessage", "Herzlich Willkommen"}
        };
    }
}
