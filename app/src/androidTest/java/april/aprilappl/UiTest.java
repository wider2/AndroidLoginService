package april.aprilappl;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.AssertionFailedError;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

/**
 * Created by Alexei on 5/04/2018.
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class UiTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testFailureLogin() throws InterruptedException {
        try {

            ViewInteraction signInButton = onView(withId(R.id.btLogin)).check(matches(isDisplayed()));
            signInButton.perform(click());

            if (doesViewExist(R.id.linkRegister)) {
                onView(withId(R.id.etUsername)).perform(typeText("test@test.zz"), closeSoftKeyboard());
                onView(withId(R.id.etPassword)).perform(typeText("Qq123456"), closeSoftKeyboard());
                onView(withId(R.id.btSignIn)).perform(click());

                Thread.sleep(4000);

                ViewInteraction view = onView(allOf(withId(R.id.toolbar_tv_account), withText("400")));
                view.check(matches(isDisplayed()));

                ViewInteraction view1 = onView(withText("400")).check(matches(isDisplayed()));

                pressBack();
            }

            Thread.sleep(1000);

        } catch (NoMatchingViewException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testSuccessLogin() throws InterruptedException {
        boolean foundMistake = false;
        try {
            ViewInteraction signInButton = onView(withId(R.id.btLogin)).check(matches(isDisplayed()));
            signInButton.perform(click());

            if (doesViewExist(R.id.linkRegister)) {
                onView(withId(R.id.etUsername)).perform(typeText("ttt@ttt.com"), closeSoftKeyboard());
                onView(withId(R.id.etPassword)).perform(typeText("Qwerty1"), closeSoftKeyboard());
                onView(withId(R.id.btSignIn)).perform(click());

                Thread.sleep(4000);

                ViewInteraction view = onView(allOf(withId(R.id.toolbar_tv_account), withText("400")));
                view.check(matches(isDisplayed()));

                try {
                    onView(withText("400")).check(matches(isDisplayed()));
                    foundMistake = true;
                } catch (AssertionFailedError e) {
                    e.printStackTrace();
                }
                if (foundMistake) {
                    pressBack();
                } else {
                    if (doesViewExist(R.id.btLogout)) {
                        Thread.sleep(1000);
                        onView(withId(R.id.btLogout)).perform(click());
                    }
                }
            }

            Thread.sleep(1000);

        } catch (NoMatchingViewException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testRegister() throws InterruptedException {
        try {

            ViewInteraction signUpButton = onView(withId(R.id.btRegister)).check(matches(isDisplayed()));
            signUpButton.perform(click());

            if (doesViewExist(R.id.btNextRegister)) {
                onView(withId(R.id.btNextRegister)).perform(click());

                if (doesViewExist(R.id.btNextRegister2)) {
                    onView(withId(R.id.etUsername)).perform(typeText("test@test.ee"), closeSoftKeyboard());
                    onView(withId(R.id.etPassword)).perform(typeText("Qwerty123"), closeSoftKeyboard());

                    onView(withId(R.id.cbTerms)).perform(click());
                    ViewInteraction checkboxView = onView(withId(R.id.cbTerms)).check(matches(is(isChecked())));
                    //onView(withId(R.id.cbTerms)).check(matches(not(isChecked())));

                    onView(withId(R.id.btNextRegister2)).perform(click());

                    if (doesViewExist(R.id.btNextRegister3)) {
                        onView(withId(R.id.etCity)).perform(typeText("Tallinn"), closeSoftKeyboard());
                        onView(withId(R.id.etZip)).perform(typeText("zip12345"), closeSoftKeyboard());

                        onView(withId(R.id.spinnerCountry)).perform(click());
                        onData(anything()).atPosition(3).perform(click());
                        onView(withId(R.id.spinnerCountry)).check(matches(withSpinnerText(containsString("Finland"))));

                        onView(withId(R.id.btNextRegister3)).perform(click());

                        if (doesViewExist(R.id.btLogout)) {
                            Thread.sleep(1000);
                            onView(withId(R.id.btLogout)).perform(click());
                            Thread.sleep(1000);
                            if (doesViewExist(R.id.btExit)) {
                                onView(withId(R.id.btExit)).perform(click());
                            }
                        }
                    }
                }
            }

            Thread.sleep(1000);

        } catch (NoMatchingViewException e) {
            e.printStackTrace();
        }
    }


    public boolean doesViewExist(int id) {
        try {
            onView(withId(id)).check(matches(isDisplayed()));
            return true;
        } catch (NoMatchingViewException e) {
            return false;
        }
    }

}
