package april.aprilappl;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import april.aprilappl.account.AccountFragmentPresenter;
import april.aprilappl.account.IAccountFragment;
import april.aprilappl.login.ILoginFragment;
import april.aprilappl.login.LoginFragment;
import april.aprilappl.login.LoginFragmentPresenter;
import april.aprilappl.model.ModelRegister;
import april.aprilappl.model.ModelResponse;
import april.aprilappl.register.IRegisterFragment;
import april.aprilappl.register.RegisterFragmentPresenter;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MockPresenterLoginTest {

    private String username = "test@test.com";
    private String password = "Qwert123";
    private String city = "Tallinn";
    private String country = "EE";
    private String zip = "12345";

    @Mock
    Context mockContext;

    @Mock
    LoginFragment loginFragment;

    private LoginFragmentPresenter loginFragmentPresenter;

    @Before
    public void setUp() throws Exception {
        mockContext = InstrumentationRegistry.getTargetContext();
        ILoginFragment mainView = mock(ILoginFragment.class);
        loginFragmentPresenter = new LoginFragmentPresenter(mainView);
    }


    @Test
    public void testMockModel() {

        AccountFragmentPresenter accountFragmentPresenter = mock(AccountFragmentPresenter.class);

        doThrow(new RuntimeException()).when(accountFragmentPresenter).loadLastSession();
    }

    @Test
    public void testMockView() {
        //create your Presenter first
        AccountFragmentPresenter accountFragmentPresenter;

        //mock an instance of MainActivityView
        IAccountFragment mainView = mock(IAccountFragment.class);

        //and attach View to the Presenter
        accountFragmentPresenter = new AccountFragmentPresenter(mainView);
        accountFragmentPresenter.loadLastSession();
    }

    @Test
    public void testServerLogin2() {

        ModelResponse modelResponse = new ModelResponse();
        modelResponse.setData("success");

        LoginFragmentPresenter spy = Mockito.spy(loginFragmentPresenter);

        doThrow(new RuntimeException()).when(spy).checkLogin(username, password);
    }

    @Test
    public void testSendRegistration() {
        ModelResponse modelResponse = new ModelResponse();
        ModelRegister modelRegister = new ModelRegister();

        IRegisterFragment iRegisterFragment = mock(IRegisterFragment.class);
        RegisterFragmentPresenter registerFragmentPresenter = new RegisterFragmentPresenter(iRegisterFragment);

        when(iRegisterFragment.refreshResult(modelResponse, modelRegister)).thenReturn(true);
        registerFragmentPresenter.postRegister(username, password, city, zip, country);

    }

}