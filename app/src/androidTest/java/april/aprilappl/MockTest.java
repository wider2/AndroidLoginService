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
import org.mockito.junit.MockitoJUnitRunner;

import april.aprilappl.account.AccountFragmentPresenter;
import april.aprilappl.utils.HelperUtils;
import april.aprilappl.utils.Utilities;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class MockTest {

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

        Assert.assertThat(true, is(result.contains("wrong")));
    }

    @Test
    public void testMockModel() {
        EditText editText = mock(EditText.class);

        HelperUtils helperUtils = mock(HelperUtils.class);

        doThrow(new RuntimeException()).when(helperUtils).HelperUtilsKeyboard(editText);
    }

}