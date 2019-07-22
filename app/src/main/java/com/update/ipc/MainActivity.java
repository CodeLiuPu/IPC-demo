package com.update.ipc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.update.ipc.bind_service.BindActivity;
import com.update.ipc.messenger.MessengerActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Update TestService";

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                startActivity(new Intent(activity, BindActivity.class));
                break;
            case R.id.btn1:
                startActivity(new Intent(activity, MessengerActivity.class));
                break;
//            case R.id.btn2:
//                startActivity(new Intent(activity, AidlActivity.class));
//                break;
//            case R.id.btn3:
//                break;
        }
    }

    private void log(String msg) {
        Log.e(TAG, msg);
    }
}
