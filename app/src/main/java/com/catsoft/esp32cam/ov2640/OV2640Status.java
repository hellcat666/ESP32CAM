package com.catsoft.esp32cam.ov2640;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.catsoft.esp32cam.ov2640.OV2640Constants.CAMERA_STATUS;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.HTTP;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.SERVER_URL;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.STATUS;

/**
 * Project: ESP32CAM
 * Package: com.catsoft.esp32cam
 * File:
 * Created by HellCat on 15.06.2021.
 */
public class OV2640Status {

    private Context mContext;
    public Context getContext() { return mContext; }
    public void setContext(Context context) { this.mContext = context; }

    private String mIpAddress;
    public String getIpAddress() { return mIpAddress; }
    public void setIpAddress(String ipAddress) { this.mIpAddress = ipAddress; }

    private String mCameraServerUrl;
    public String getCameraServerUrl() { return mCameraServerUrl; }
    public void setCameraServerUrl(String cameraServerUrl) { this.mCameraServerUrl = cameraServerUrl; }

    private int mCameraStatus;
    public int getCameraStatus() { return mCameraStatus; }
    public void setCameraStatus(int cameraStatus) { this.mCameraStatus = cameraStatus; }

    private String mPingCommand;
    public String getPingCommand() { return mPingCommand; }
    public void setPingCommand(String pingCommand) { this.mPingCommand = pingCommand; }

    private int mInterval;
    public int getInterval() { return mInterval; }
    public void setInterval(int interval) { this.mInterval = interval; }

    private boolean mRunning;
    public boolean isRunning() { return mRunning;}
    public void setRunning(boolean running) { this.mRunning = running; }

    private Process mProcess;
    public Process getProcess() { return mProcess; }
    public void setProcess(Process process) { this.mProcess = process; }

    OV2640Status(Context context, String ip, int interval) {
        mContext = context;
        mIpAddress = ip;
        mCameraServerUrl = HTTP + mIpAddress;
        mPingCommand = new String("ping -w 2 ") + mIpAddress;
        mInterval = interval;
        mRunning = false;
    }

    public void start() {
        mRunning = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(mRunning) {
                    mCameraStatus = ping();
                    Intent anIntent = new Intent();
                    anIntent.setAction( CAMERA_STATUS );
                    anIntent.putExtra( SERVER_URL, mCameraServerUrl );
                    anIntent.putExtra( STATUS, (mCameraStatus == 0) );
                    mContext.sendBroadcast( anIntent );
                    try {
                        Thread.sleep( mInterval );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void stop() {
        mRunning = false;
    }

    private int ping() {
    int retCode = -1;
        try {
            String[] cmdline = {"sh", "-c", mPingCommand};
            mProcess = Runtime.getRuntime().exec( cmdline );
            String line = null;

            InputStream stderr = mProcess.getErrorStream();
            InputStreamReader esr = new InputStreamReader( stderr );
            BufferedReader ebr = new BufferedReader( esr );
            while ((line = ebr.readLine()) != null)
                Log.e( "PING", line );

            InputStream stdout = mProcess.getInputStream();
            InputStreamReader osr = new InputStreamReader( stdout );
            BufferedReader obr = new BufferedReader( osr );
            while ((line = obr.readLine()) != null)
                Log.i( "PING", line );

            retCode = mProcess.waitFor();
            Log.d( "PING", "getprop exitValue: " + retCode );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retCode;
    }

}
