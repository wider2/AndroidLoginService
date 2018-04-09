package april.aprilappl;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.support.test.InstrumentationRegistry;
import android.support.v4.content.LocalBroadcastManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReceiverTest {

    private ConnectivityReceiver connectivityReceiver;
    private Context context;

    @Before
    public void setUp() throws Exception {
        connectivityReceiver = new ConnectivityReceiver();
        context = InstrumentationRegistry.getTargetContext();

        LocalBroadcastManager.getInstance(context).registerReceiver(
                connectivityReceiver, new IntentFilter(Intent.ACTION_PACKAGE_REPLACED));

    }

    @After
    public void tearDown() throws Exception {

        LocalBroadcastManager.getInstance(context).unregisterReceiver(connectivityReceiver);
    }

    @Test
    public void testStartActivity() throws InterruptedException {

        boolean isConnected = ConnectivityReceiver.isConnected();

        assertEquals(true, isConnected);
    }

}
