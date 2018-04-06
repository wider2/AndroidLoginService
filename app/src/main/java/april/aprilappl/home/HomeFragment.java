package april.aprilappl.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;

import april.aprilappl.ExitActivity;
import april.aprilappl.R;
import april.aprilappl.SharedStatesMap;
import april.aprilappl.login.LoginFragment;
import april.aprilappl.register.RegisterFragment;
import april.aprilappl.utils.ToolbarHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private Unbinder unbinder;
    ToolbarHelper toolbarHelper;
    SharedStatesMap sharedStates;

    @BindView(R.id.tvOutput)
    TextView tvOutput;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @AfterViews
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, v);

        //it is just the singleton to store some data between fragments
        sharedStates = SharedStatesMap.getInstance();
        sharedStates.setKey("regStage", "");
        sharedStates.setKey("regUsername", "");
        sharedStates.setKey("regPassword", "");
        sharedStates.setKey("regCountry", "");
        sharedStates.setKey("regCountryName", "");
        sharedStates.setKey("regCity", "");
        sharedStates.setKey("regZip", "");

        tvTitle.setText(getString(R.string.welcome, getString(R.string.app_name)));

        try {
            toolbarHelper = ToolbarHelper.from(getActivity(), v.findViewById(R.id.toolbar))
                    .title(getString(R.string.app_name))
                    .account("")
                    .progressBarColorRes(R.color.yellow)
                    .insetsFrom(R.id.cl_root);

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

    @OnClick(R.id.btLogin)
    void submitLoginButton() {
        try {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .add(R.id.home_fragment_container, new LoginFragment(), LoginFragment.class.getName())
                    .addToBackStack(LoginFragment.class.getName())
                    .commit();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            tvOutput.setText(ex.getMessage());
        }
    }

    @OnClick(R.id.btRegister)
    void submitRegisterButton() {
        sharedStates.setKey("regStage", "1");

        try {
            getActivity().getSupportFragmentManager()
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

    @OnClick(R.id.btExit)
    void submitExitButton() {
        ExitActivity.exitApplication(getContext());
    }

}