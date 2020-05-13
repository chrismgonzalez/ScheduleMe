package Resources;

import java.util.ListResourceBundle;
//this class will alert an incorrect login attempt in German (DEU) in Microsoft Windows 10
public class Login_de extends ListResourceBundle {
    protected Object[][] getContents() {
        return new Object[][] {
                { "username", "Nutzername" },
                { "password", "Passwort" },
                { "login", "Anmeldung" },
                { "error_invalid", "Ungültiger Benutzername oder Passwort" },
                { "close", "Schließen" },
                { "loginMessage", "Herzlich Willkommen" }
        };
    }
}
