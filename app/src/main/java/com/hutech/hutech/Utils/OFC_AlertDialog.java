package com.hutech.hutech.Utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.hutech.hutech.R;

/**
 * Created by Raghavendra on 6/3/2021.
 */
public class OFC_AlertDialog extends AlertDialog.Builder {
     private Activity activity;
    public OFC_AlertDialog(Activity context) {
        super(context);
        activity = context;
    }
    public void showAlertDialog(String title, String message, String positiveButtonText,
                                DialogInterface.OnClickListener positiveListener, String negativeButtonText,
                                DialogInterface.OnClickListener negetiveListener, boolean isCancellable,
                                boolean cancelOnTouchOutside) {
        try {
//            Typeface regular = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
  //          Typeface medium = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Medium.ttf");

            if (title != null) {
               // setTitle(setTypeface(medium, title));
                setTitle(title);
            }
            if (message != null) {
                // Linkify the message
              //  Linkify.addLinks(new SpannableString(message), Linkify.ALL);
               // setMessage(setTypeface(regular, Html.fromHtml("<font color='#615D5D'>"+message+"</font>")));
                setMessage(message);
            }
            if (negativeButtonText != null) {
                setNegativeButton(negativeButtonText, negetiveListener);
            }
            if (positiveButtonText != null) {
                setPositiveButton(positiveButtonText, positiveListener);
            }
            setCancelable(isCancellable);
            final AlertDialog dialog = create();
            dialog.setCanceledOnTouchOutside(cancelOnTouchOutside);
            if (!activity.isFinishing()) {
                dialog.show();
            }
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ActivityCompat.getColor(getContext(), R.color.black));
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ActivityCompat.getColor(getContext(), R.color.black));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SpannableString setTypeface(Typeface typeface, CharSequence string) {
        SpannableString s = new SpannableString(string);
        s.setSpan(new TypefaceSpan(typeface), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    private static class TypefaceSpan extends MetricAffectingSpan {
        private final Typeface typeface;
        public TypefaceSpan(Typeface typeface) {
            this.typeface = typeface;
        }
        @Override
        public void updateDrawState(TextPaint tp) {
            tp.setTypeface(typeface);
            tp.setFlags(tp.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        @Override
        public void updateMeasureState(TextPaint p) {
            p.setTypeface(typeface);
            p.setFlags(p.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
    }
}
