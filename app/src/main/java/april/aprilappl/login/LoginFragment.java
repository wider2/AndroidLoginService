package april.aprilappl.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.androidannotations.annotations.AfterViews;

import april.aprilappl.R;
import april.aprilappl.account.AccountFragment;
import april.aprilappl.model.ModelResponse;
import april.aprilappl.register.RegisterFragment;
import april.aprilappl.utils.KeyboardUtility;
import april.aprilappl.utils.ToolbarHelper;
import april.aprilappl.utils.Utilities;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;

public class LoginFragment extends Fragment implements ILoginFragment {

    private static final String TAG = LoginFragment.class.getSimpleName();
    private Unbinder unbinder;
    LoginFragmentPresenter loginFragmentPresenter;
    ToolbarHelper toolbarHelper;

    @BindView(R.id.tvOutput)
    TextView tvOutput;
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btSignIn)
    TextView btSignIn;

    @BindView(R.id.toolbar_progressbar)
    ProgressBar progressBar;
    @BindView(R.id.toolbar_tv_account)
    TextView tvAccount;

    @BindView(R.id.usernameWrapper)
    TextInputLayout usernameWrapper;
    @BindView(R.id.passwordWrapper)
    TextInputLayout passwordWrapper;

    @AfterViews
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        try {
            unbinder = ButterKnife.bind(this, v);
            loginFragmentPresenter = new LoginFragmentPresenter(this);

            toolbarHelper = ToolbarHelper.from(getActivity(), v.findViewById(R.id.toolbar))
                    .title(getString(R.string.sign_in))
                    .progressBarColorRes(R.color.yellow)
                    .insetsFrom(R.id.ll_root)
                    .colorRes(R.color.n_orange);

        } catch (Exception ex) {
            ex.printStackTrace();
            tvOutput.setText(ex.getMessage());
        }
        return v;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) unbinder.unbind();
    }

    @OnClick(R.id.btSignIn)
    public void submitLoginButtonOnly() {
        usernameWrapper.setError("");
        passwordWrapper.setError("");
        String username, password;
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        if (!Utilities.isValidEmail(username)) {
            usernameWrapper.setError(getString(R.string.email_error));
        } else if (!Utilities.isPasswordValid(password)) {
            passwordWrapper.setError(getString(R.string.password_error));
        } else {
            tvOutput.setText(getString(R.string.please_wait));
            progressBar.setVisibility(View.VISIBLE);

            KeyboardUtility.setHideSoftKeyboard(getContext(), etPassword);

            loginFragmentPresenter.checkLogin(username, password);
        }
    }

    @UiThread
    @Override
    public void refreshResult(ModelResponse jsonResponse) {
        tvOutput.setText("");
        progressBar.setVisibility(View.GONE);
        try {
            ((FragmentActivity) getActivity()).getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .add(R.id.home_fragment_container, new AccountFragment(), AccountFragment.class.getName())
                    .addToBackStack(AccountFragment.class.getName())
                    .commit();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            tvOutput.setText(ex.getMessage());
        }
    }

    @OnClick(R.id.linkRegister)
    void submitRegisterButton(View view) {
        try {
            ((FragmentActivity) getActivity()).getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .add(R.id.home_fragment_container, new RegisterFragment(), RegisterFragment.class.getName())
                    .addToBackStack(RegisterFragment.class.getName())
                    .commit();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            tvOutput.setText(ex.getMessage());
        }
    }

    @UiThread
    @Override
    public void showException(Exception ex) {
        tvOutput.setText(getString(R.string.server_response, ex.getMessage(), ex.getStackTrace().toString()));
        progressBar.setVisibility(View.GONE);
    }

    @UiThread
    @Override
    public void showErrorServerResponse(Throwable throwable) {
        try {
            String phrase, msg = throwable.getMessage();

            if (throwable instanceof HttpException) {
                final HttpException httpException = (HttpException) throwable;
                ResponseBody body = httpException.response().errorBody();
                tvAccount.setText(String.valueOf(httpException.code()));

                phrase = Utilities.errorPhrase(getContext(), body.source().toString());
                tvOutput.setText(getString(R.string.error_occurred, httpException.code(), httpException.response().message(), phrase));
            } else {
                tvOutput.setText(getString(R.string.server_response, msg, throwable.getStackTrace().toString()));
            }
            progressBar.setVisibility(View.GONE);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            tvOutput.append(ex.getMessage());
        }
    }

}