package april.aprilappl.account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import april.aprilappl.R;
import april.aprilappl.home.HomeFragment;
import april.aprilappl.model.ModelLogin;
import april.aprilappl.utils.ToolbarHelper;
import april.aprilappl.utils.Utilities;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class AccountFragment extends Fragment implements IAccountFragment {

    private static final String TAG = AccountFragment.class.getSimpleName();

    private Unbinder unbinder;
    ToolbarHelper toolbarHelper;
    AccountFragmentPresenter accountFragmentPresenter;

    @BindView(R.id.tv_output)
    TextView tvOutput;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.toolbar_tv_account)
    TextView tvAccount;

    @BindView(R.id.toolbar_progressbar)
    ProgressBar progressBar;


    @AfterViews
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        try {
            unbinder = ButterKnife.bind(this, v);
            accountFragmentPresenter = new AccountFragmentPresenter(this);

            toolbarHelper = ToolbarHelper.from(getActivity(), v.findViewById(R.id.toolbar))
                    .title(getString(R.string.account))
                    .account("")
                    .progressBarColorRes(R.color.yellow)
                    .insetsFrom(R.id.cl_root)
                    .colorRes(R.color.n_orange);

            getActivity().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            progressBar.setVisibility(View.VISIBLE);
            accountFragmentPresenter.loadLastSession();

        } catch (Exception ex) {
            ex.printStackTrace();
            tvOutput.setText(ex.getMessage());
        }
        return v;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.btLogout)
    void submitLogoutButton() {
        try {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.home_fragment_container, new HomeFragment(), HomeFragment.class.getName())
                    .commit();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    @UiThread
    @Override
    public void refreshResult(ModelLogin modelLogin, List<ModelLogin> listLogins) {
        long timeStamp = 0;
        String username = modelLogin.getUsername();
        tvTitle.setText(getString(R.string.welcome_to_account, username));
        tvAccount.setText(Utilities.getNameEllipsize(username));
        progressBar.setVisibility(View.GONE);

        //just to show previous login visit
        if (!listLogins.isEmpty()) {
            if (listLogins.size() > 0) {
                for (int i = 0; i < listLogins.size(); i++) {
                    if (i == 1) timeStamp = listLogins.get(i).getLastVisit();
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date theDate = new Date(timeStamp);
                if (theDate.toString().startsWith("01/01/1970")) {
                    tvOutput.setText(getString(R.string.first_login));
                } else {
                    tvOutput.setText(getString(R.string.last_login, sdf.format(theDate)));
                }
            }
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
        String msg = throwable.getMessage();
        tvOutput.setText(msg);
        progressBar.setVisibility(View.GONE);
    }

}