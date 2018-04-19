package april.aprilappl;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4.class)
public class MyUiAutomatorTest {

    private final static String TAG = MyUiAutomatorTest.class.getName();
    Context context;
    private UiDevice device;
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp() {

        context = InstrumentationRegistry.getInstrumentation().getContext();

        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        assertThat(device, notNullValue());

        device.pressHome();
    }

    private void openApp(String packageName) {

        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Test
    public void test() throws InterruptedException, UiObjectNotFoundException {
        openApp("april.aprilappl");

        UiObject signInButton = device.findObject(new UiSelector().text("Sign in"));
        signInButton.clickAndWaitForNewWindow();

        UiObject etUsername = device.findObject(new UiSelector().className("android.widget.EditText"));
        etUsername.setText("qqq@qqq.com");

        UiObject etPassword = device.findObject(new UiSelector().className("android.widget.EditText").enabled(true).instance(1));
        etPassword.setText("123456");

        UiObject loginObject = device.findObject(new UiSelector().text("Sign in"));
        loginObject.click();

        Thread.sleep(5000);

        device.pressBack();
        device.pressBack();


        UiObject signUpButton = device.findObject(new UiSelector().text("Sign up"));
        signUpButton.clickAndWaitForNewWindow();

        //UiObject nextButton = device.findObject(new UiSelector().text(context.getString(R.string.next)));
        UiObject nextButton = device.findObject(new UiSelector().text("Next"));
        nextButton.clickAndWaitForNewWindow();

        UiObject regUsername = device.findObject(new UiSelector().className("android.widget.EditText").enabled(true).instance(0));
        regUsername.setText("qqq@qqq.tv");

        UiObject regPassword = device.findObject(new UiSelector().className("android.widget.EditText").enabled(true).instance(1));
        regPassword.setText("Qwerty1");

        //click at checkbox
        new UiSelector().clickable(true).checkable(true).index(0);
        UiObject checkboxTerms = device.findObject(new UiSelector().className("android.widget.CheckBox"));
        checkboxTerms.click();

        UiObject nextObject = device.findObject(new UiSelector().text("Next"));
        nextObject.click();


        UiObject regCity = device.findObject(new UiSelector().className("android.widget.EditText").enabled(true).instance(0));
        regCity.setText("Tallinn");

        UiObject regZip = device.findObject(new UiSelector().className("android.widget.EditText").enabled(true).instance(1));
        regZip.setText("12345");


        UiObject spinnerLayout = device.findObject(new UiSelector().className("android.widget.Spinner"));
        spinnerLayout.click();

        UiSelector mainLayout = new UiSelector().description("Country");
        mainLayout.childSelector(new UiSelector().className(android.widget.Spinner.class.getName()).focusable(true));

        UiObject spinnerObject = device.findObject(new UiSelector().text("Finland"));
        spinnerObject.click();

/*
        BySelector spinnerCountry = (By.text("Finland"));
        device.findObject(new UiSelector().className("android.widget.Spinner")).click();
        List<UiObject2> children = device.findObjects(By.res("android:id/spinnerCountry").pkg("april.aprilappl"));
        for (UiObject2 uio2 : children) {
            Log.wtf(TAG, uio2.getText());
            UiObject2 curObject = uio2.findObject(By.text("Finland"));
            if (uio2.getText().equals("Finland")) {
                uio2.click();
                break;
            }
        }
*/
        UiObject nextObject3 = device.findObject(new UiSelector().text("Sign up"));
        nextObject3.click();

        Thread.sleep(9000);


        String checkText = "Congratulation! You have registered successfully.";
        UiObject regValidation = device.findObject(new UiSelector().text(checkText));
        assertTrue(checkText, regValidation.exists());

        Thread.sleep(3000);
    }

}
