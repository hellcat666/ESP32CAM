package com.catsoft.esp32cam;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static com.catsoft.esp32cam.ov2640.OV2640Constants.CAMERA_NAME;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.NAME;


/**
 * Project: ESP32CAM
 * Package: com.catsoft.esp32cam
 * File:
 * Created by HellCat on 14.06.2021.
 */
public class ESP32CameraStatusBar {

    static final String TAG = "ESP32CameraStatusBar";

    public static final String CAMERA_STATUS = "camera-status";
    public static final String CAMERA_STATE = "camera-state";
    public static final String STATUS = "status";

    private static View mView = null;
    private TextView mCameraLabel = null;
    private ImageView mCameraLed = null;
    static private Context mContext = null;
    private IntentFilter intentFilter = null;

    private String mCameraName = "----------";
    private boolean mCameraOnLine = false;

    ESP32CameraStatusBar(View view) {
        this.mView = view;
        this.mContext = mView.getContext();
        initialize();
        registerReceiver();
        setCameraName();
        setCameraStatus();
    }

    void initialize() {
        mCameraOnLine = false;
        mCameraLabel = (TextView)mView.findViewById(R.id.lblCamera);
        mCameraLed = (ImageView)mView.findViewById(R.id.ledView);
    }

    void registerReceiver(){
        Log.i(TAG, "registerReceiver()");
        intentFilter = new IntentFilter();
        intentFilter.addAction(CAMERA_STATUS);
        intentFilter.addAction(CAMERA_NAME);
        mContext.registerReceiver(messageReceiver, intentFilter);
    }

    void unregisterReceiver() {
        Log.i(TAG, "unregisterReceiver()");
        mContext.unregisterReceiver(messageReceiver);
    }

    private void setCameraName() {
        mCameraLabel.setText( mCameraName );
    }

    private void setCameraStatus() {
        if(mCameraOnLine) {
            mCameraLabel.setTextColor(mContext.getResources().getColor(R.color.white, null));
            mCameraLed.setImageResource(R.drawable.green_led_on);
        }
        else {
            mCameraLabel.setTextColor(mContext.getResources().getColor(R.color.red, null));
            mCameraLed.setImageResource(R.drawable.red_led_on);
        }
    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent==null) return;

            switch (intent.getAction()) {
                case CAMERA_NAME:
                    Log.i(TAG, "CAMERA_NAME Received ...");
                    mCameraName = intent.getExtras().getString( NAME );
                    setCameraName();
                    break;
                case CAMERA_STATUS:
                    Log.i(TAG, "CAMERA_STATUS Received ...");
                    mCameraOnLine  = intent.getExtras().getBoolean( STATUS );
                    setCameraStatus();
                    break;
            }
        }
    };
}
