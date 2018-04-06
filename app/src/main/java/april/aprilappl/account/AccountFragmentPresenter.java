package april.aprilappl.account;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import april.aprilappl.LoginDatabase;
import april.aprilappl.SignInApplication;
import april.aprilappl.dagger.DaggerLibComponent;
import april.aprilappl.dagger.LibModule;
import april.aprilappl.dagger.LibRepository;
import april.aprilappl.model.ModelLogin;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AccountFragmentPresenter {

    private static final String TAG = AccountFragmentPresenter.class.getSimpleName();
    private List<ModelLogin> listLogins;
    private IAccountFragment mainView;
    private Context context;
    private LoginDatabase loginDatabase;


    public AccountFragmentPresenter(IAccountFragment mainView) {
        this.mainView = mainView;

        DaggerLibComponent.builder().libModule(new LibModule()).build();

        context = new SignInApplication().getSignInApplication();
        loginDatabase = LoginDatabase.getInstance(context);
    }


    public void loadLastSession() {
        try {
            Observable.fromCallable(new Callable<ModelLogin>() {
                @Override
                public ModelLogin call() {
                    listLogins = loginDatabase.loginDao().loadRecentLogins();

                    return loginDatabase.loginDao().loadLastLogin();
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ModelLogin>() {
                        @Override
                        public void accept(ModelLogin modelLogin) throws Exception {
                            mainView.refreshResult(modelLogin, listLogins);
                        }
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
            mainView.showException(ex);
        }
    }

}