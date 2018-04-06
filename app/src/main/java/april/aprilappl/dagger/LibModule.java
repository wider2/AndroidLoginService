package april.aprilappl.dagger;

import android.content.Context;

import javax.inject.Singleton;

import april.aprilappl.SignInApplication;
import dagger.Module;
import dagger.Provides;

@Module
public class LibModule {

    @Provides
    public LibRepository getLibRepository() {
        return new LibRepository();
    }

}