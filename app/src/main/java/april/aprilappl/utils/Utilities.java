package april.aprilappl.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import april.aprilappl.R;

public class Utilities {

    private static boolean isTextValid(String text) {
        boolean valid = true;
        if (TextUtils.isEmpty(text)) {
            valid = false;
        } else {
            if (text.length() < 10 || text.length() > 80) {
                valid = false;
            }
        }
        return valid;
    }

    public static boolean isValidEmail(String email) {
        if (isTextValid(email)) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return false;
    }

    public static boolean isPasswordValid(String password) {
        boolean valid = true;
        if (TextUtils.isEmpty(password)) {
            valid = false;
        } else {
            if ((password.length() < 6 || password.length() > 35)
                    || (password.toLowerCase().equals(password)) || (password.toUpperCase().equals(password))
                    || (!password.matches(".*\\d.*"))) {
                valid = false;
            }
        }
        return valid;
    }

    public static boolean isValidCity(String text) {
        boolean valid = true;
        if (TextUtils.isEmpty(text)) {
            valid = false;
        } else {
            if (text.length() < 5 || text.length() > 50) {
                valid = false;
            }
        }
        return valid;
    }

    public static boolean isValidZip(String text) {
        boolean valid = true;
        if (TextUtils.isEmpty(text)) {
            valid = false;
        } else {
            if (text.length() < 5 || text.length() > 10) {
                valid = false;
            }
        }
        return valid;
    }

    public static String errorPhrase(Context context, String phrase) {
        String result = "";
        if (phrase.contains("err.wrong.credentials")) {
            result = context.getString(R.string.err_wrong_credentials);
        } else if (phrase.contains("err.password.too.short")) {
            result = context.getString(R.string.err_password_too_short);
        } else if (phrase.contains("err.timeout")) {
            result = context.getString(R.string.err_timeout);
        } else if (phrase.contains("err.user.exists")) {
            result = context.getString(R.string.err_user_exists);
        } else {
            result = phrase;
        }
        return result;
    }

    public static String getNameEllipsize(String username) {
        String result = "";
        if (!TextUtils.isEmpty(username)) {
            if (username.length() > 5) result = username.substring(0, 5);
        }
        return result;
    }
}