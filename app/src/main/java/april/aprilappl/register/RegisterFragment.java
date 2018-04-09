package april.aprilappl.register;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.androidannotations.annotations.AfterViews;

import java.util.ArrayList;

import april.aprilappl.R;
import april.aprilappl.SharedStatesMap;
import april.aprilappl.model.Country;
import april.aprilappl.model.ModelRegister;
import april.aprilappl.model.ModelResponse;
import april.aprilappl.utils.KeyboardUtility;
import april.aprilappl.utils.ToolbarHelper;
import april.aprilappl.utils.Utilities;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;

public class RegisterFragment extends Fragment implements IRegisterFragment {

    private static final String TAG = RegisterFragment.class.getSimpleName();
    String regStage, titleRegister, username, password, city, zip;
    View mainView;
    private Unbinder unbinder;
    ToolbarHelper toolbarHelper;
    SharedStatesMap sharedStates;
    RegisterFragmentPresenter registerFragmentPresenter;
    SharedPreferences prefs;

    //can't use BindView here, because one fragment contains 3 layout
    CheckBox cbTerms;
    EditText etUsername, etPassword, etCity, etZip;
    TextInputLayout usernameWrapper, passwordWrapper, cityWrapper, zipWrapper;
    Button btNextRegister, btNextRegister2, btNextRegister3;
    Spinner spinnerCountry;
    ArrayList<Country> countryList;

    @BindView(R.id.tvOutput)
    TextView tvOutput;

    @BindView(R.id.toolbar_tv_account)
    TextView tvAccount;
    @BindView(R.id.toolbar_progressbar)
    ProgressBar progressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sharedStates = SharedStatesMap.getInstance();
        regStage = sharedStates.getKey("regStage");
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        try {
            if (regStage.equals("3")) {

                mainView = inflater.inflate(R.layout.fragment_register3, container, false);
                unbinder = ButterKnife.bind(this, mainView);
                titleRegister = getString(R.string.sign_up3);

                etCity = (EditText) mainView.findViewById(R.id.etCity);
                etZip = (EditText) mainView.findViewById(R.id.etZip);
                cityWrapper = (TextInputLayout) mainView.findViewById(R.id.cityWrapper);
                zipWrapper = (TextInputLayout) mainView.findViewById(R.id.zipWrapper);
                btNextRegister3 = (Button) mainView.findViewById(R.id.btNextRegister3);
                btNextRegister3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        submitNextRegister3();
                    }
                });

                city = prefs.getString("city", "");
                zip = prefs.getString("zip", "");
                etCity.setText(city);
                etZip.setText(zip);

                spinnerCountry = (Spinner) mainView.findViewById(R.id.spinnerCountry);
                //try to remove left padding of Spinner. Pointless
                spinnerCountry.setPadding(0, spinnerCountry.getPaddingTop(), spinnerCountry.getPaddingRight(), spinnerCountry.getPaddingBottom());

                countryList = new ArrayList<>();
                countryList.add(new Country("", getString(R.string.select_list)));
                countryList.add(new Country("EE", "Estonia"));
                countryList.add(new Country("RU", "Russia"));
                countryList.add(new Country("FI", "Finland"));
                countryList.add(new Country("SE", "Sweden"));

                ArrayAdapter<Country> adapter = new ArrayAdapter<Country>(getContext(), android.R.layout.simple_spinner_item, countryList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCountry.setAdapter(adapter);
                spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Country country = (Country) parent.getSelectedItem();
                        String state_id = country.getId(), state = country.getName();
                        sharedStates.setKey("regCountry", state_id);
                        sharedStates.setKey("regCountryName", state);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                username = prefs.getString("username", "");
                username = Utilities.getNameEllipsize(username);

            } else if (regStage.equals("2")) {

                mainView = inflater.inflate(R.layout.fragment_register2, container, false);
                unbinder = ButterKnife.bind(this, mainView);
                titleRegister = getString(R.string.sign_up2);

                cbTerms = (CheckBox) mainView.findViewById(R.id.cbTerms);
                etUsername = (EditText) mainView.findViewById(R.id.etUsername);
                etPassword = (EditText) mainView.findViewById(R.id.etPassword);
                usernameWrapper = (TextInputLayout) mainView.findViewById(R.id.usernameWrapper);
                passwordWrapper = (TextInputLayout) mainView.findViewById(R.id.passwordWrapper);
                btNextRegister2 = (Button) mainView.findViewById(R.id.btNextRegister2);
                btNextRegister2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        submitNextRegister2();
                    }
                });

                username = prefs.getString("username", "");
                password = prefs.getString("password", "");
                etUsername.setText(username);
                etPassword.setText(password);
                KeyboardUtility.showKeyboard(etUsername);

            } else {

                mainView = inflater.inflate(R.layout.fragment_register, container, false);
                unbinder = ButterKnife.bind(this, mainView);
                titleRegister = getString(R.string.sign_up1);
                btNextRegister = (Button) mainView.findViewById(R.id.btNextRegister);
                btNextRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        submitNextRegister();
                    }
                });
            }

            registerFragmentPresenter = new RegisterFragmentPresenter(this);

            toolbarHelper = ToolbarHelper.from(getActivity(), mainView.findViewById(R.id.toolbar))
                    .title(titleRegister)
                    .account(username)
                    .progressBarColorRes(R.color.yellow)
                    .insetsFrom(R.id.ll_root);

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

    private void submitNextRegister() {
        sharedStates.setKey("regStage", "2");

        try {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.home_fragment_container, new RegisterFragment(), RegisterFragment.class.getName())
                    .addToBackStack(RegisterFragment.class.getName())
                    .commit();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            tvOutput.setText(ex.getMessage());
        }
    }

    private void submitNextRegister2() {
        usernameWrapper.setError("");
        passwordWrapper.setError("");

        String username, password;
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();


        SharedPreferences.Editor editPref = prefs.edit();
        editPref.putString("username", username);
        editPref.putString("password", password);
        editPref.apply();


        if (!Utilities.isValidEmail(username)) {
            usernameWrapper.setError(getString(R.string.email_error));
        } else if (!Utilities.isPasswordValid(password)) {
            passwordWrapper.setError(getString(R.string.password_error));
        } else if (!cbTerms.isChecked()) {
            tvOutput.setText(getString(R.string.confirm_terms_service));
        } else {
            sharedStates.setKey("regStage", "3");
            sharedStates.setKey("regUsername", username);
            sharedStates.setKey("regPassword", password);

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.home_fragment_container, new RegisterFragment(), RegisterFragment.class.getName())
                    .commit();
        }
    }

    private void submitNextRegister3() {
        cityWrapper.setError("");
        zipWrapper.setError("");
        tvOutput.setText("");

        String city, zip, username, password, country;
        city = etCity.getText().toString();
        zip = etZip.getText().toString();
        sharedStates.setKey("regCity", city);
        sharedStates.setKey("regZip", zip);

        country = sharedStates.getKey("regCountry");
        username = sharedStates.getKey("regUsername");
        password = sharedStates.getKey("regPassword");

        SharedPreferences.Editor editPref = prefs.edit();
        editPref.putString("city", city);
        editPref.putString("zip", zip);
        editPref.apply();

        if (!Utilities.isValidCity(city)) {
            cityWrapper.setError(getString(R.string.city_error));
        } else if (!Utilities.isValidZip(zip)) {
            zipWrapper.setError(getString(R.string.zip_error));
        } else if (TextUtils.isEmpty(country)) {
            tvOutput.setText(getString(R.string.country_error));
        } else {
            progressBar.setVisibility(View.VISIBLE);
            KeyboardUtility.setHideSoftKeyboard(getContext(), etZip);

            registerFragmentPresenter.postRegister(username, password, city, zip, country);
        }
    }

    @UiThread
    @Override
    public boolean refreshResult(ModelResponse modelResponse, ModelRegister modelRegister) {
        boolean success;

        if (!modelRegister.getUsername().equals("")) {
            success = true;
            tvOutput.setText(getString(R.string.reg_succeeded));
            progressBar.setVisibility(View.GONE);
            try {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.home_fragment_container, new RegisteredFragment(), RegisteredFragment.class.getName())
                        .addToBackStack(RegisteredFragment.class.getName())
                        .commit();
            } catch (NullPointerException ex) {
                ex.printStackTrace();
                tvOutput.setText(ex.getMessage());
                success = false;
            }
        } else {
            success = false;
            tvOutput.setText(getString(R.string.reg_failed));
        }
        return success;
    }

    @UiThread
    @Override
    public void statusLoading(int status) {
        if (status == 1) {
            progressBar.setVisibility(View.VISIBLE);
            tvOutput.setText(getString(R.string.please_wait));
        } else {
            progressBar.setVisibility(View.GONE);
            tvOutput.setText("");
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