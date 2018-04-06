package april.aprilappl;

import android.app.Application;

import april.aprilappl.dagger.DaggerLibComponent;
import april.aprilappl.dagger.LibComponent;
import april.aprilappl.dagger.LibModule;

/**
 * Created by Aleksei Jegorov on 3/4/2018.
 */
public class SignInApplication extends Application {

    private static final String TAG = SignInApplication.class.getSimpleName();
    private static SignInApplication signInApplication;
    private LoginDatabase loginDatabase;

    private LibComponent libComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        signInApplication = this;

        libComponent = DaggerLibComponent.builder()
                .libModule(new LibModule())
                .build();

        loginDatabase = libComponent.getLibRepository().provideDatabase(signInApplication);
    }

    public static SignInApplication getApplication() {
        return signInApplication;
    }

    public SignInApplication getSignInApplication() {
        return signInApplication;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

}