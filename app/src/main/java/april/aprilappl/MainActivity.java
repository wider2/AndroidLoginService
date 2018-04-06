package april.aprilappl;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import april.aprilappl.home.HomeFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity
        implements ConnectivityReceiver.ConnectivityReceiverListener {

    private Unbinder unbinder;
    Snackbar snackBar;

    @BindView(R.id.tvOutput)
    TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);
        checkConnection();

        //if change orientation
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.home_fragment_container, new HomeFragment(), HomeFragment.class.getName())
                    .commit();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) checkConnection();

        SignInApplication.getApplication().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        if (!isConnected) {
            snackBar = Snackbar
                    .make(tvOutput, getString(R.string.no_connection), Snackbar.LENGTH_INDEFINITE)
                    .setActionTextColor(getResources().getColor(R.color.yellow))
                    .setAction(getString(R.string.network_settings), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });

            View sbView = snackBar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackBar.show();
        } else {
            if (snackBar != null) snackBar.dismiss();
        }
    }

}
