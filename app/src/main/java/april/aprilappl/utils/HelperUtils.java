package april.aprilappl.utils;

import android.content.Context;
import android.widget.EditText;

public class HelperUtils {

    public String HelperUtilsPhrase(Context context, String phrase) {
        return Utilities.errorPhrase(context, phrase);
    }

    public void HelperUtilsKeyboard(EditText editText) {
        KeyboardUtility.showKeyboard(editText);
    }

}