package april.aprilappl.register;

import android.content.Context;

import java.util.Calendar;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import april.aprilappl.LoginDatabase;
import april.aprilappl.SignInApplication;
import april.aprilappl.dagger.DaggerLibComponent;
import april.aprilappl.dagger.LibModule;
import april.aprilappl.dagger.LibRepository;
import april.aprilappl.model.ModelRegister;
import april.aprilappl.model.ModelResponse;
import april.aprilappl.model.PostRegister;
import april.aprilappl.utils.RetrofitApi;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RegisterFragmentPresenter {

    private static final String TAG = RegisterFragmentPresenter.class.getSimpleName();
    private IRegisterFragment mainView;
    private Context context;
    private LoginDatabase loginDatabase;
    private Retrofit retrofit;

    @Inject
    LibRepository libRepository;

    public RegisterFragmentPresenter(IRegisterFragment mainView) {
        this.mainView = mainView;

        DaggerLibComponent.builder().libModule(new LibModule()).build();
        this.libRepository = DaggerLibComponent.create().getLibRepository();

        context = new SignInApplication().getSignInApplication();
        loginDatabase = LoginDatabase.getInstance(context);
    }


    public void postRegister(final String username, final String password, final String city, final String zip, final String country) {
        try {
            retrofit = libRepository.provideRetrofit();
            RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

            PostRegister postRegister = new PostRegister();
            postRegister.setEmail(username);
            postRegister.setPassword(password);
            postRegister.setCountry(country);
            postRegister.setCity(city);
            postRegister.setPostal_code(zip);

            retrofitApi.postRegister(postRegister)
                    .distinctUntilChanged()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            mainView.statusLoading(1);
                        }
                    })
                    .doOnTerminate(new Action() {
                        @Override
                        public void run() throws Exception {
                            mainView.statusLoading(0);
                        }
                    })
                    .subscribe(new Consumer<ModelResponse>() {
                        @Override
                        public void accept(ModelResponse modelResponse) throws Exception {

                            Calendar now = Calendar.getInstance();

                            ModelRegister modelRegister = new ModelRegister();
                            modelRegister.setUsername(username);
                            modelRegister.setPassword(password);
                            modelRegister.setCity(city);
                            modelRegister.setZip(zip);
                            modelRegister.setCountry(country);
                            modelRegister.setDateRegister(now.getTimeInMillis());

                            registerToDatabase(modelResponse, modelRegister);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            mainView.showErrorServerResponse(throwable);
                        }
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
            mainView.showException(ex);
        }
    }

    private void registerToDatabase(final ModelResponse modelResponse, final ModelRegister modelRegister) {

        Observable.fromCallable(new Callable<ModelRegister>() {
            @Override
            public ModelRegister call() {
                loginDatabase.loginDao().insertRegister(modelRegister);
                return loginDatabase.loginDao().loadLastRegistration();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ModelRegister>() {
                    @Override
                    public void accept(ModelRegister modelRegister) throws Exception {
                        mainView.refreshResult(modelResponse, modelRegister);
                    }
                });
    }

}