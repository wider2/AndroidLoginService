package april.aprilappl.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import april.aprilappl.SignInApplication;

public class KeyboardUtility {

    public static void setHideSoftKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void showKeyboard(EditText editText) {
        try {
            if (editText != null) {
                InputMethodManager imm = (InputMethodManager) SignInApplication.getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);

                editText.requestFocus();
                editText.setVisibility(View.VISIBLE);
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
