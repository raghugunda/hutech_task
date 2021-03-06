package com.hutech.hutech.base;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hutech.hutech.Utils.Constants_urls;

/**
 * Created by Raghavendra on 6/3/2021.
 */
public class OFC_BaseActivity extends AppCompatActivity implements Constants_urls {

    public OFC_PrefsManager ofcPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ofcPreferences = new OFC_PrefsManager(this);
    }

    public void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(OFC_BaseActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showToastInCenter(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast t = Toast.makeText(OFC_BaseActivity.this, message, Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();;
            }
        });
    }
}

