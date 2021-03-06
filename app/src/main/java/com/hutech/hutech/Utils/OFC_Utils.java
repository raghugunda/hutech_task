package com.hutech.hutech.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hutech.hutech.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Raghavendra on 6/3/2021.
 */
public class OFC_Utils {

    public static Dialog pDialog;
    private String picture_directory = "/picturedir/";

    // This method deals with the Network Change. i.e., Device in Online or not.
    public static boolean isOnline(Context activity) {
        if (activity != null) {
            NetworkInfo info = ((ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            return (info != null && info.getState() == NetworkInfo.State.CONNECTED);
        }
        return false;
    }

    // This method will fired when there is no network
    public static void showNoNetworkAlert(final Activity act, boolean isActivityFinish) {
        if (act != null) {
            showPopup(act, "", act.getString(R.string.no_network_message), isActivityFinish);
        }
    }



    public static void showProgressDialog(Activity activity, String title, String message) {
        if (activity == null) {
            return;
        }
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.cancel();
            pDialog.dismiss();
        }
        pDialog = new Dialog(activity/*, android.R.style.Theme_DeviceDefault_Dialog_MinWidth*/);
        pDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.progress_dialog_layout);
        pDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        if (title != null && title.length() >= 1) {
            ((TextView) pDialog.findViewById(R.id.titleTextView)).setText(title);
        } else {
            ((TextView) pDialog.findViewById(R.id.titleTextView)).setVisibility(View.GONE);
        }
        TextView tvMessage = (TextView) pDialog.findViewById(R.id.messageTextView);
        if (message != null && message.isEmpty()) {
            tvMessage.setVisibility(View.GONE);
        } else if (message != null) {
            (tvMessage).setText(message);
        }
        LinearLayout dialogContainer = (LinearLayout) pDialog.findViewById(R.id.dialogContainer);
       // OFC_Utils.doApplyFont(activity.getAssets(), dialogContainer);
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(10);
        shape.setColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            dialogContainer.setBackground(shape);
        } else {
            dialogContainer.setBackgroundDrawable(shape);
        }
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);

        if (!pDialog.isShowing()) {
            pDialog.show();

            pDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            pDialog.cancel();
            pDialog.dismiss();
            pDialog.show();
        }
    }

    public static void hideProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.cancel();
            pDialog.dismiss();
            pDialog = null;
        }
    }

    public static void showPopup(final Activity act, String title, String message, final boolean activityFinish) {
        if (act == null || act.isFinishing()) {
            return;
        }
        OFC_AlertDialog dialog = new OFC_AlertDialog(act);
        dialog.showAlertDialog(title, message, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                if (activityFinish) {
                    act.finish();
                    //   act.overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
                }
            }
        }, null, null, false, false);
    }





    public static void hideKeyboard(Activity act) {
        try {
            InputMethodManager inputManager = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = act.getCurrentFocus();
            if (view != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }















}
