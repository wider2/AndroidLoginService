package april.aprilappl;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import april.aprilappl.model.ModelLogin;
import april.aprilappl.model.ModelRegister;
import april.aprilappl.utils.GlobalConstants;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Alexei on 5/04/2018.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    LoginDatabase db;

    @Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        db = LoginDatabase.getInstance(appContext);
    }

    @After
    public void tearDown() throws Exception {
        if (db != null) {
            if (db.isOpen()) db.close();
        }
    }

    @Test
    public void checkDatabase() throws Exception {

        ModelRegister modelRegister = db.loginDao().loadLastRegistration();

        assertNotNull(modelRegister);
    }

    @Test
    public void insertRegistrationToDatabase() throws Exception {
        String username = "Tester";
        String password = "Test123";
        String city = "Tallinn";
        String zip = "12345";
        String country = "Estonia";

        Calendar now = Calendar.getInstance();

        ModelRegister modelRegister = new ModelRegister();
        modelRegister.setUsername(username);
        modelRegister.setPassword(password);
        modelRegister.setCity(city);
        modelRegister.setZip(zip);
        modelRegister.setCountry(country);
        modelRegister.setDateRegister(now.getTimeInMillis());

        db.loginDao().insertRegister(modelRegister);

        ModelRegister result = db.loginDao().loadByUsername(username);
        assertThat(true, is(result.getUsername().equals(username)));
    }

    @Test
    public void updateToDatabase() throws Exception {
        String username = "Tester";
        String city = "Maardu";

        ModelRegister result = db.loginDao().loadByUsername(username);
        assertNotNull(result);

        db.loginDao().updateRegistration(username, city);

        ModelRegister modelRegister = db.loginDao().loadByUsername(username);
        assertEquals(city, modelRegister.getCity());
    }


    @Test
    public void checkLoginOfDatabase() throws Exception {
        String username = "Tester";
        ModelLogin result = db.loginDao().loadLoginByUsername(username);
        assertNull(result);
    }

    @Test
    public void checkLastLoginsOfDatabase() throws Exception {

        Date dt = GlobalConstants.addHours(-5);

        List<ModelLogin> result = db.loginDao().checkLastVisits(dt.getTime());
        assertThat(true, is(result.size() > 1));
    }

    @Test
    public void insertLoginToDatabase() throws Exception {
        String username = "Jumbo";
        String password = "Jumbo123";

        Calendar now = Calendar.getInstance();

        ModelLogin modelLogin = new ModelLogin();
        modelLogin.setUsername(username);
        modelLogin.setPassword(password);
        modelLogin.setLastVisit(now.getTimeInMillis());

        db.loginDao().insertLogin(modelLogin);

        ModelLogin result = db.loginDao().loadLoginByUsername(username);
        assertThat(true, is(result.getUsername().equals(username)));
    }
}
