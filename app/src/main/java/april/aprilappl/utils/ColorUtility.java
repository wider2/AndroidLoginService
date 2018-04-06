package april.aprilappl.utils;

import android.graphics.Color;
import android.support.annotation.ColorRes;

import april.aprilappl.SignInApplication;


public class ColorUtility {

    public static int getColor(@ColorRes int res) {
        return SignInApplication.getApplication().getResources().getColor(res);
    }

}