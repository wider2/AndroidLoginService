package april.aprilappl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.MockitoJUnitRunner;

import april.aprilappl.account.AccountFragmentPresenter;
import april.aprilappl.account.IAccountFragment;
import april.aprilappl.login.ILoginFragment;
import april.aprilappl.login.LoginFragment;
import april.aprilappl.login.LoginFragmentPresenter;
import april.aprilappl.model.ModelResponse;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class MockPresenterLoginTest {

    private String username = "test@test.com";
    private String password = "Qwert123";

    @Mock
    LoginFragment loginFragment;

    LoginFragmentPresenter loginFragmentPresenter;

    @Before
    public void setUp() throws Exception {

        ILoginFragment mainView = mock(ILoginFragment.class);
        loginFragmentPresenter = new LoginFragmentPresenter(mainView);
    }


    @Test
    public void testMockModel() {

        AccountFragmentPresenter accountFragmentPresenter = mock(AccountFragmentPresenter.class);

        MockitoException exception = new MockitoException("test");
        doThrow(exception).when(accountFragmentPresenter).loadLastSession();
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

        MockitoException exception = new MockitoException("test");

        LoginFragmentPresenter spy = Mockito.spy(loginFragmentPresenter);

        doThrow(exception).when(spy).checkLogin(username, password);
    }

}