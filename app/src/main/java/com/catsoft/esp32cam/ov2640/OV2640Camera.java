package com.catsoft.esp32cam.ov2640;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.catsoft.esp32cam.ESP32Camera;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.catsoft.esp32cam.ESP32CameraSettingsDialog.TAG;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.CAMERA_REFRESH;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.CAMERA_STATUS;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.HTTP;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.REBOOT;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.SERVER_URL;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.STATUS;


/**
 * Project: ESP32CAM
 * Package: com.catsoft.esp32cam
 * File:
 * Created by HellCat on 15.06.2021.
 */
public class OV2640Camera {

    static final int TWO_SECONDS = 2000;

    Context mContext;
    public Context getContext() { return mContext; }
    public void setContext(Context context) { this.mContext = context; }

    String mCameraName;
    public String getCameraName() { return mCameraName; }
    public void setCameraName(String cameraName) { this.mCameraName = cameraName; }
    String mIpAddress;

    String mCameraUrl;
    public String getCameraUrl() { return mCameraUrl; }
    public void setCameraUrl(String cameraUrl) { this.mCameraUrl = cameraUrl; }

    boolean mCameraOnline;
    public boolean isCameraOnline() { return mCameraOnline; }
    public void setCameraOnline(boolean cameraOnline) { this.mCameraOnline = cameraOnline; }

    private int mStatusInterval;
    public int getStatusInterval() { return mStatusInterval; }
    public void setStatusInterval(int statusInterval) { this.mStatusInterval = statusInterval; }

    OV2640Status mCameraStatus;
    public OV2640Status getCameraStatus() { return mCameraStatus; }
    public void setCameraStatus(OV2640Status cameraStatus) { this.mCameraStatus = cameraStatus; }

    OV2640Settings mCameraSettings;
    public OV2640Settings getCameraSettings() { return mCameraSettings; }
    public void setCameraSettings(OV2640Settings cameraSettings) { this.mCameraSettings = cameraSettings; }

    private IntentFilter mIntentFilter = null;

    public OV2640Camera(String name, String ip) {
        mContext =  ESP32Camera.getContext();

        mCameraName = name;
        mIpAddress = ip;

        mCameraUrl = HTTP + mIpAddress;
        mCameraOnline = false;

        initCameraStatus();
        initCameraSettings();
        registerReceiver();
    }

    private void initCameraStatus() {
        mStatusInterval = TWO_SECONDS;
        mCameraStatus = new OV2640Status(mContext, mIpAddress, mStatusInterval);
        mCameraStatus.start();
    }

    private void initCameraSettings() {
        mCameraSettings = new OV2640Settings( mContext, mIpAddress);
    }

    private void registerReceiver() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(CAMERA_STATUS);
        mIntentFilter.addAction( REBOOT );
        mContext.registerReceiver( messageReceiver, mIntentFilter );
    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent==null) return;

            switch(intent.getAction()) {
                case CAMERA_STATUS:
                    if(mCameraUrl.equalsIgnoreCase( intent.getExtras().getString(SERVER_URL) )) {
                        boolean status = intent.getExtras().getBoolean( STATUS );
                        if(mCameraOnline!=status) {
                            mCameraOnline = status;
                            if (mCameraOnline) {
                                Log.i( TAG, mCameraName + "/" + mIpAddress + " is Online" );
                            } else {
                                Log.i( TAG, mCameraName + "/" + mIpAddress + " is Offline" );
                            }
                            Intent anIntent = new Intent();
                            anIntent.putExtra(STATUS, mCameraOnline);
                            anIntent.setAction(CAMERA_REFRESH);
                            anIntent.putExtra( SERVER_URL, mCameraUrl );
                            mContext.sendBroadcast( anIntent );
                        }
                    }
                    break;
                case REBOOT:
                    rebootCamera();
            }
        }
    };

    private void rebootCamera() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(mCameraOnline) {
                    String settings = null;
                    try {
                        URL url = new URL( mCameraUrl + "/reboot" );
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        try {
                            try (BufferedReader br = new BufferedReader(
                                    new InputStreamReader( urlConnection.getInputStream(), "utf-8" ) )) {
                                StringBuilder response = new StringBuilder();
                                String responseLine = null;
                                while ((responseLine = br.readLine()) != null) {
                                    response.append( responseLine.trim() );
                                }
                                System.out.println( response.toString() );
                                settings = response.toString();
                            }
                        } catch (IOException ioex) {
                            ioex.printStackTrace();
                        } finally {
                            urlConnection.disconnect();
                        }
                    }
                    catch (IOException ioex) {
                        ioex.printStackTrace();
                    }
                    finally {
                    }
                }
            }
        }).start();

    }

}
