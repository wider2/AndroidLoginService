package april.aprilappl.dagger;

import android.app.Application;
import android.arch.core.BuildConfig;
import android.arch.persistence.room.Room;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import april.aprilappl.LoginDatabase;
import dagger.Reusable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static april.aprilappl.utils.GlobalConstants.SERVER_SSL_URL;


@Reusable
public class LibRepository {

    @Inject
    public LibRepository() {
    }

    @Singleton
    public Retrofit provideRetrofit() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .build();

        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(SERVER_SSL_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Singleton
    public LoginDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                LoginDatabase.class, "DatabaseLogin.db")
                .build();
    }

}
