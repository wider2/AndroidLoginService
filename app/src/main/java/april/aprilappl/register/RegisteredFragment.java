package april.aprilappl.register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;

import april.aprilappl.R;
import april.aprilappl.SharedStatesMap;
import april.aprilappl.home.HomeFragment;
import april.aprilappl.utils.ToolbarHelper;
import april.aprilappl.utils.Utilities;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegisteredFragment extends Fragment {

    private static final String TAG = RegisteredFragment.class.getSimpleName();
    String titleRegister, username, password, city, zip, country;
    View mainView;
    private Unbinder unbinder;
    ToolbarHelper toolbarHelper;
    SharedStatesMap sharedStates;

    @BindView(R.id.tvOutput)
    TextView tvOutput;

    @BindView(R.id.tvUsername)
    TextView tvUsername;
    @BindView(R.id.tvPassword)
    TextView tvPassword;
    @BindView(R.id.tvCountry)
    TextView tvCountry;
    @BindView(R.id.tvCity)
    TextView tvCity;
    @BindView(R.id.tvZip)
    TextView tvZip;

    @AfterViews
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sharedStates = SharedStatesMap.getInstance();
        mainView = inflater.inflate(R.layout.fragment_registered, container, false);

        try {
            sharedStates = SharedStatesMap.getInstance();
            username = sharedStates.getKey("regUsername");
            password = sharedStates.getKey("regPassword");
            country = sharedStates.getKey("regCountryName");
            city = sharedStates.getKey("regCity");
            zip = sharedStates.getKey("regZip");

            unbinder = ButterKnife.bind(this, mainView);

            toolbarHelper = ToolbarHelper.from(getActivity(), mainView.findViewById(R.id.toolbar))
                    .title(getString(R.string.registered))
                    .account(Utilities.getNameEllipsize(username))
                    .progressBarColorRes(R.color.yellow)
                    .insetsFrom(R.id.cl_root);

            tvUsername.setText(username);
            tvPassword.setText(password);
            tvCountry.setText(country);
            tvCity.setText(city);
            tvZip.setText(zip);

        } catch (Exception ex) {
            ex.printStackTrace();
            tvOutput.setText(ex.getMessage());
        }
        return mainView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.btLogout)
    void submitLogoutButton(View view) {
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

}