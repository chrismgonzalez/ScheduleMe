package Utils;

import java.time.ZoneId;
import java.util.Locale;


//retrieve the user timezone and locale
public class UserSettings {
    public static String userName;
    public static int userId;
    public static Locale locale = Locale.getDefault();
    public static final ZoneId USER_TIME_ZONE = ZoneId.systemDefault();
}
