package april.aprilappl.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ProgressBar;
import android.widget.TextView;

import april.aprilappl.R;


public class ToolbarHelper {

    private static final String LOG_TAG = ToolbarHelper.class.getSimpleName();
    private final ProgressBar progressBar;
    private TextView tvTitle, tvAccount;

    private final Activity a;
    private final View toolbar;
    private final Resources res;
    private Context context;

    private ToolbarHelper(final Activity activity, View v) {
        a = activity;
        res = a.getResources();
        if (v != null) {
            toolbar = v.findViewById(R.id.toolbar);
        } else {
            toolbar = a.findViewById(R.id.toolbar);
        }
        tvTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        progressBar = (ProgressBar) toolbar.findViewById(R.id.toolbar_progressbar);
        tvAccount = (TextView) toolbar.findViewById(R.id.toolbar_tv_account);

        //if toolbar gets top insets only
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                    WindowInsets topInset = insets.replaceSystemWindowInsets(new Rect(0, insets.getSystemWindowInsetTop(), 0, 0));
                    return v.onApplyWindowInsets(topInset);
                }
            });
        }
    }

    public static ToolbarHelper from(Activity a) {
        return new ToolbarHelper(a, null);
    }

    public static ToolbarHelper from(Activity a, View v) {
        return new ToolbarHelper(a, v);
    }

    public ToolbarHelper title(int titleResId) {
        return title(res.getString(titleResId));
    }

    public ToolbarHelper title(String title) {
        if (tvTitle != null) {
            if (title == null) {
                title = "";
            }
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
        return this;
    }

    public ToolbarHelper account(String title) {
        if (tvAccount != null) {
            if (title == null) {
                title = "";
            }
            tvAccount.setVisibility(View.VISIBLE);
            tvAccount.setText(title);
        }
        return this;
    }

    public ToolbarHelper colorRes(@ColorRes int resId) {
        return colorInt(res.getColor(resId));
    }

    public ToolbarHelper colorInt(int color) {
        if (toolbar != null) {
            toolbar.setBackgroundColor(color);
        }
        return this;
    }

    public ToolbarHelper setContext(Context ctx) {
        context = ctx;
        return this;
    }

    public ToolbarHelper progressBarColorRes(@ColorRes int colorRes) {
        if (progressBar != null) {
            progressBar.getIndeterminateDrawable().setColorFilter(ColorUtility.getColor(colorRes), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        return this;
    }

    public ToolbarHelper insetsFrom(@IdRes int parentViewId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View contentView = a.findViewById(parentViewId);
            if (contentView != null) {
                contentView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                        return v.onApplyWindowInsets(toolbar.onApplyWindowInsets(insets));
                    }
                });
            } else {
                Log.e(ToolbarHelper.class.getSimpleName(), "Can't find view to apply insets listener");
            }
        }
        return this;
    }

}