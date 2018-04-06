package april.aprilappl.utils;

import java.util.Calendar;
import java.util.Date;

public class GlobalConstants {

    public static final String SERVER_SSL_URL = "https://poco-test.herokuapp.com/";

    public static Date addHours(int value) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR, value);
        return now.getTime();
    }

}