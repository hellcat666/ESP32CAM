package com.catsoft.esp32cam;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

/**
 * Project: CatGPSLocation
 * Package: com.catsoft.catgpslocation
 * File:
 * Created by HellCat on 18.02.2019.
 */
public class ViewDialogSpinningWheel {

    private ProgressDialog mDialog;
    private Activity mActivity;
    private String mTitle = "";
    private TextView mMessageTextView;
    private String mMessage = "";
    private boolean mVisible = false;

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setMessage(String msg) {
        mMessage = msg;
    }


    public void showDialog(Activity activity) {
        mActivity = activity;
        mDialog = new ProgressDialog(mActivity, R.style.ProgressDlgTheme); // this = YourActivity
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setTitle(mTitle);
        mDialog.setMessage(mMessage);
        mDialog.setIndeterminate(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(activity.getBaseContext(), R.drawable.background_border_red));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = new ProgressBar(mActivity).getIndeterminateDrawable().mutate();
            drawable.setColorFilter(ContextCompat.getColor(mActivity, R.color.blue),
                    PorterDuff.Mode.SRC_IN);
            mDialog.setIndeterminateDrawable(drawable);
        }

        mDialog.show();
    }

    public void hideDialog() {
        if(mDialog.isShowing()) {
            mDialog.hide();
        }
        try {
            this.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public boolean isVisible() {
        return mDialog.isShowing();
    }
}
