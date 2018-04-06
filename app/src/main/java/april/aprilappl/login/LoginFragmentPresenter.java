package april.aprilappl.login;

import android.content.Context;

import java.util.Calendar;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import april.aprilappl.LoginDatabase;
import april.aprilappl.SignInApplication;
import april.aprilappl.dagger.DaggerLibComponent;
import april.aprilappl.dagger.LibModule;
import april.aprilappl.dagger.LibRepository;
import april.aprilappl.model.ModelLogin;
import april.aprilappl.model.ModelResponse;
import april.aprilappl.model.PostLogin;
import april.aprilappl.utils.RetrofitApi;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LoginFragmentPresenter {

    private static final String TAG = LoginFragmentPresenter.class.getSimpleName();
    private ILoginFragment mainView;
    private Context context;
    private LoginDatabase loginDatabase;
    private Retrofit retrofit;

    @Inject
    LibRepository libRepository;

    public LoginFragmentPresenter(ILoginFragment mainView) {
        this.mainView = mainView;

        DaggerLibComponent.builder().libModule(new LibModule()).build();
        this.libRepository = DaggerLibComponent.create().getLibRepository();

        context = new SignInApplication().getSignInApplication();
        loginDatabase = LoginDatabase.getInstance(context);
    }


    public void checkLogin(final String username, final String password) {
        try {
            //dagger call Singleton instance of retrofit
            retrofit = libRepository.provideRetrofit();
            RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

            //crete new item to be send
            PostLogin postLogin = new PostLogin(username, password);

            retrofitApi.sendLoginPost(postLogin)
                    .distinctUntilChanged()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<ModelResponse>() {
                        @Override
                        public void accept(ModelResponse modelResponse) throws Exception {

                            Calendar now = Calendar.getInstance();

                            ModelLogin modelLogin = new ModelLogin();
                            modelLogin.setUsername(username);
                            modelLogin.setPassword(password);
                            modelLogin.setLastVisit(now.getTimeInMillis());

                            insertToDatabase(modelResponse, modelLogin);

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            mainView.showErrorServerResponse(throwable);
                        }
                    });
        } catch (Exception ex) {
            mainView.showException(ex);
        }
    }

    private void insertToDatabase(final ModelResponse modelResponse, final ModelLogin modelLogin) {

        Observable.fromCallable(new Callable<ModelLogin>() {
            @Override
            public ModelLogin call() {
                loginDatabase.loginDao().insertLogin(modelLogin);

                return loginDatabase.loginDao().loadLastLogin();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ModelLogin>() {
                    @Override
                    public void accept(ModelLogin modelLogin) throws Exception {
                        mainView.refreshResult(modelResponse);
                    }
                });
    }

}