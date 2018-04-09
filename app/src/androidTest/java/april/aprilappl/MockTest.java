package april.aprilappl;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.widget.EditText;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Calendar;

import april.aprilappl.account.AccountFragmentPresenter;
import april.aprilappl.model.ModelLogin;
import april.aprilappl.utils.HelperUtils;
import april.aprilappl.utils.Utilities;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class MockTest {

    private String username = "test@test.com";
    private String password = "Qwert123";

    @Mock
    Context mockContext;

    @Before
    public void setUp() throws Exception {
        mockContext = InstrumentationRegistry.getTargetContext();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testMockPhrase() throws Exception {
        String mockString = "err.wrong.credentials";

        HelperUtils mockResult = mock(HelperUtils.class);
        Mockito.when(mockResult.HelperUtilsPhrase(mockContext, mockString)).thenReturn(mockString);

        String result = Utilities.errorPhrase(mockContext, mockString);

        assertThat(true, is(result.contains("wrong")));
    }

    @Test
    public void testMockModel() {
        EditText editText = mock(EditText.class);

        HelperUtils helperUtils = mock(HelperUtils.class);

        doThrow(new RuntimeException()).when(helperUtils).HelperUtilsKeyboard(editText);
    }

    @Test
    public void testUpdate() {
        ModelLogin mockModelLogin = mock(ModelLogin.class);
        Calendar now = Calendar.getInstance();

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                if (arguments != null && arguments.length > 1 && arguments[0] != null && arguments[1] != null) {

                    ModelLogin modelLogin = (ModelLogin) arguments[0];
                    String email = (String) arguments[1];
                    modelLogin.setUsername(email);
                }
                return null;
            }
        }).when(mockModelLogin).setModelLogin(any(String.class), any(String.class), any(Long.class));

        // call the method under test
        ModelLogin modelLogin = new ModelLogin();
        modelLogin.setUsername(username);
        modelLogin.setPassword(password);
        modelLogin.setLastVisit(now.getTimeInMillis());

        assertThat(modelLogin, is(notNullValue()));
        assertThat(modelLogin.getUsername(), is(equalTo(username)));
    }

}