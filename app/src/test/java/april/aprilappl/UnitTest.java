package april.aprilappl;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import april.aprilappl.utils.GlobalConstants;
import april.aprilappl.utils.Utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class UnitTest {


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testEmail() throws Exception {
        String email = "email@.ee";

        boolean result = Utilities.isValidEmail(email);

        assertEquals(false, result);
    }

    @Test
    public void testDate() throws Exception {
        int value = -5;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + value);
        Date dt = cal.getTime();

        Date result = GlobalConstants.addHours(value);

        assertSame(dt, result);
    }

    @Test
    public void testPasswordValid() throws Exception {
        String password = "fishFish1";

        boolean result = Utilities.isPasswordValid(password);

        assertEquals(true, result);
    }


}