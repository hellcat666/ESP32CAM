package com.catsoft.esp32cam.ov2640;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.catsoft.esp32cam.ov2640.OV2640Constants.AEC;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.AEC2;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.AE_LEVEL;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.AGC;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.AGC_GAIN;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.AWB;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.AWB_GAIN;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.BPC;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.BRIGHTNESS;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.CAMERA_SETTINGS;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.CAMERA_STATUS;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.COLOR_BAR;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.COMMAND_STATUS;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.CONTRAST;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.DCW;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.EFFECT;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.FACE_DETECTION;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.FACE_RECOGNITION;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.FRAMESIZE;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.GAIN_CEILING;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.HTTP;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.H_MIRROR;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.LENS_CORRECTION;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.QUALITY;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.RAW_GMA;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.REQUEST_SETTINGS;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.SATURATION;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.SETTING;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.SETTINGS;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.SETTINGS_RECEIVED;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.SET_SETTING;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.STATUS;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.VALUE;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.V_FLIP;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.WBMODE;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.WPC;

/**
 * Project: AndroidDialogTest
 * Package: com.catsoft.androiddialogtest
 * File:
 * Created by HellCat on 18.05.2021.
 */
public class OV2640Settings  implements Parcelable {

    private static final String TAG = "OV2640Settings";
    private static final String USER_AGENT = "Mozilla/5.0";
    private final OV2640Settings _self = this;

    private Context mContext;
    private String mCameraSettingsUrl;
    private String mCameraControlUrl;
    private boolean mCameraOnline;
    private String mCommand;
    private String mValue;
    private int mResponseCode;

    private JSONObject  mJSONSettingsData;
    private boolean mDataInitialized;
    private IntentFilter mIntentFilter = null;

    private int framesize;
    public int getFramesize() { return framesize; }
    public void setFramesize(int framesize) {
        this.framesize = framesize;
    }

    private Integer quality;
    public Integer getQuality() { return quality; }
    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    private int brightness;
    public int getBrightness() { return brightness; }
    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    private int contrast;
    public int getContrast() { return contrast; }
    public void setContrast(int contrast) {
        this.contrast = contrast;
    }

    private int saturation;
    public int getSaturation() { return saturation; }
    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    private int effect;
    public int getEffect() { return effect; }
    public void setEffect(int effect) {
        this.effect = effect;
    }

    private int awb;
    public int getAwb() { return awb; }
    public void setAwb(int awb) {
        this.awb = awb;
    }

    private int awbGain;
    public int getAwbGain() { return awbGain; }
    public void setAwbGain(int awbGain) {
        this.awbGain = awbGain;
    }

    private int wbMode;
    public int getWbMode() { return wbMode; }
    public void setWbMode(int wbMode) {
        this.wbMode = wbMode;
    }

    private int aec;
    public int getAec() { return aec; }
    public void setAec(int aec) {
        this.aec = aec;
    }

    private int aec2;
    public int getAec2() { return aec2; }
    public void setAec2(int aec2) {
        this.aec2 = aec2;
    }

    private int aeLevel;
    public int getAeLevel() { return aeLevel; }
    public void setAeLevel(int aeLevel) {
        this.aeLevel = aeLevel;
    }

    private int aecValue;
    public int getAecValue() { return aecValue; }
    public void setAecValue(int aecValue) {
        this.aecValue = aecValue;
    }

    private int agc;
    public int getAgc() { return agc; }
    public void setAgc(int agc) {
        this.agc = agc;
    }


    private int agcGain;
    public int getAgcGain() { return agcGain; }
    public void setAgcGain(int agcGain) {
        this.agcGain = agcGain;
    }

    private int gainCeiling;
    public int getGainCeiling() { return gainCeiling; }
    public void setGainCeiling(int gainCeiling) {
        this.gainCeiling = gainCeiling;
    }

    private int bpc;
    public int getBpc() { return bpc; }
    public void setBpc(int bpc) {
        this.bpc = bpc;
    }

    private int wpc;
    public int getWpc() { return wpc; }
    public void setWpc(int wpc) {
        this.wpc = wpc;
    }

    private int rawGma;
    public int getRawGma() { return rawGma; }
    public void setRawGma(int rawGma) {
        this.rawGma = rawGma;
    }

    private int lensCorrection;
    public int getLensCorrection() { return lensCorrection; }
    public void setLensCorrection(int lensCorrection) {
        this.lensCorrection = lensCorrection;
    }

    private int hMirror;
    public int getHMirror() { return hMirror; }
    public void setHMirror(int hMirror) {
        this.hMirror = hMirror;
    }

    private int vFlip;
    public int getVFlip() { return vFlip; }
    public void setVFlip(int vFlip) {
        this.vFlip = vFlip;
    }

    private int dcw;
    public int getDcw() { return dcw; }
    public void setDcw(int dcw) {
        this.dcw = dcw;
    }

    private int colorBar;
    public int getColorBar() { return colorBar; }
    public void setColorBar(int colorBar) { this.colorBar = colorBar; }

    private int faceDetection;
    public int getFaceDetection() { return faceDetection; }
    public void setFaceDetection(int faceDetection) {
        this.faceDetection = faceDetection;
    }

    private int faceRecognition;
    public int getFaceRecognition() { return faceRecognition; }
    public void setFaceRecognition(int faceRecognition) {
        this.faceRecognition = faceRecognition;
    }

    private void registerReceiver() {
        mIntentFilter = new IntentFilter( );
        mIntentFilter.addAction(SETTINGS_RECEIVED);
        mIntentFilter.addAction(CAMERA_STATUS);
        mIntentFilter.addAction(REQUEST_SETTINGS);
        mIntentFilter.addAction(SET_SETTING);
        mIntentFilter.addAction(COMMAND_STATUS);
        mContext.registerReceiver(messageReceiver, mIntentFilter);
    }

    private void unregisterReceiver() {
        mContext.unregisterReceiver( messageReceiver );
    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent == null) return;

            switch (intent.getAction()) {
                case SETTINGS_RECEIVED:
                    String settings = intent.getExtras().getString( SETTINGS );
                    if((settings!=null) && (!settings.isEmpty())) {
                        Log.i(TAG, "VALID DATA RECEIVED");
                        setSettings( settings );
                    }
                    else {
                        Log.i(TAG, "NO OR INVALID DATA RECEIVED");
                    }
                    break;
                case REQUEST_SETTINGS:
                    Log.i(TAG, "REQUEST_SETTING Received");
                    Intent anIntent = new Intent();
                    anIntent.setAction( CAMERA_SETTINGS );
                    anIntent.putExtra( SETTINGS, _self );
                    mContext.sendBroadcast( anIntent );
                    break;
                case SET_SETTING:
                    mCommand = intent.getExtras().getString( SETTING );
                    mValue = intent.getExtras().getString( VALUE );
                    setSetting(mCommand, mValue);
                    break;
                case COMMAND_STATUS:
                    boolean status = intent.getExtras().getBoolean( STATUS );
                    Log.i(TAG, String.format( "HTTP Request: %s", (status==true) ? "SUCCESS" : "ERROR"));
                    break;
                case CAMERA_STATUS:
                    mCameraOnline = intent.getExtras().getBoolean(STATUS);
                    if(mCameraOnline && !mDataInitialized) {
                        mDataInitialized = true;
                        refresh();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    // Constructor
    public OV2640Settings(Context context, String url){
        this.mContext = context;
        this.mCameraSettingsUrl = HTTP + url + "/settings";
        this.mCameraControlUrl = HTTP + url + "/control";
        mCameraOnline = false;
        mDataInitialized = false;
        registerReceiver();
    }

    public void refresh() {
        if(mCameraOnline)
            getSettings();
    }

    private void setSetting(final String command, final String value) {
        boolean retCode = false;

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean retCode = false;
                HttpURLConnection connection = null;
                try {
                    URL url = new URL( String.format( "%s?cmd=%s&val=%s", mCameraControlUrl, command, value ) );
                    try {
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod( "GET" );
                        connection.setRequestProperty( "User-Agent", USER_AGENT );
                        mResponseCode = connection.getResponseCode();
                        System.out.println( "GET Response Code :: " + mResponseCode );
                        if (mResponseCode == HttpURLConnection.HTTP_OK) { // success
                            Log.i( TAG, "SUCCESS" );
                            retCode = true;
                        } else { // error
                            Log.i( TAG, "ERROR" );
                            retCode = false;
                        }
                    } catch (IOException ioex) {
                        ioex.printStackTrace();
                        retCode = false;
                    } finally {
                        connection.disconnect();
                        Intent anIntent = new Intent();
                        anIntent.setAction(COMMAND_STATUS);
                        anIntent.putExtra( STATUS, retCode );
                        mContext.sendBroadcast( anIntent );
                    }
                } catch (MalformedURLException muex) {
                    muex.printStackTrace();
                    retCode = false;
                }
            }
        }).start();
    }

    private void setSettings(String settings) {
        try {
            mJSONSettingsData = new  JSONObject(settings);
            setFramesize( mJSONSettingsData.getInt(FRAMESIZE));
            setQuality( mJSONSettingsData.getInt(QUALITY));
            setBrightness( mJSONSettingsData.getInt(BRIGHTNESS));
            setContrast( mJSONSettingsData.getInt(CONTRAST));
            setSaturation( mJSONSettingsData.getInt(SATURATION));
            setEffect( mJSONSettingsData.getInt(EFFECT));
            setAwb( mJSONSettingsData.getInt(AWB));
            setAwbGain( mJSONSettingsData.getInt(AWB_GAIN));
            setWbMode( mJSONSettingsData.getInt(WBMODE));
            setAec( mJSONSettingsData.getInt(AEC));
            setAec2( mJSONSettingsData.getInt(AEC2));
            setAeLevel( mJSONSettingsData.getInt(AE_LEVEL));
            setAgc( mJSONSettingsData.getInt(AGC));
            setAgcGain( mJSONSettingsData.getInt(AGC_GAIN));
            setGainCeiling( mJSONSettingsData.getInt(GAIN_CEILING));
            setBpc( mJSONSettingsData.getInt(BPC));
            setWpc( mJSONSettingsData.getInt(WPC));
            setRawGma( mJSONSettingsData.getInt(RAW_GMA));
            setLensCorrection( mJSONSettingsData.getInt(LENS_CORRECTION));
            setHMirror( mJSONSettingsData.getInt(H_MIRROR));
            setVFlip( mJSONSettingsData.getInt(V_FLIP));
            setDcw( mJSONSettingsData.getInt(DCW));
            setColorBar( mJSONSettingsData.getInt(COLOR_BAR));
            setFaceDetection( mJSONSettingsData.getInt(FACE_DETECTION ));
            setFaceRecognition( mJSONSettingsData.getInt(FACE_RECOGNITION));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getSettings()  {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(mCameraOnline) {
                    String settings = null;
                    try {
                        URL url = new URL( mCameraSettingsUrl );
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
                            Intent anIntent = new Intent();
                            anIntent.setAction( SETTINGS_RECEIVED );
                            anIntent.putExtra( SETTINGS, settings );
                            mContext.sendBroadcast( anIntent );
                            urlConnection.disconnect();
                        }
                    } catch (IOException ioex) {
                        ioex.printStackTrace();
                    } finally {
                    }
                }
            }
        }).start();
    }

    private void sendSettings(String action, String setting) {
        Intent anIntent = new Intent();
        anIntent.setAction( action );
        anIntent.putExtra( VALUE, setting);
        mContext.sendBroadcast( anIntent );
    }


    /**
     * Parcelable Implementation
     */
    public static final Creator<OV2640Settings> CREATOR = new Creator<OV2640Settings>() {
        @Override
        public OV2640Settings createFromParcel(Parcel in) {
            return new OV2640Settings(in);
        }

        @Override
        public OV2640Settings[] newArray(int size) {
            return new OV2640Settings[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt( getFramesize() );
        dest.writeInt( getQuality() );
        dest.writeInt( getBrightness() );
        dest.writeInt( getContrast() );
        dest.writeInt( getSaturation() );
        dest.writeInt( getEffect() );
        dest.writeInt( getWbMode() );
        dest.writeInt( getAwb() );
        dest.writeInt( getAwbGain() );
        dest.writeInt( getAec() );
        dest.writeInt( getAec2() );
        dest.writeInt( getAeLevel() );
        dest.writeInt( getAecValue() );
        dest.writeInt( getAgc() );
        dest.writeInt( getAgcGain() );
        dest.writeInt( getGainCeiling() );
        dest.writeInt( getBpc() );
        dest.writeInt( getWpc() );
        dest.writeInt( getRawGma() );
        dest.writeInt( getLensCorrection() );
        dest.writeInt( getVFlip() );
        dest.writeInt( getHMirror() );
        dest.writeInt( getDcw() );
        dest.writeInt( getColorBar() );
        dest.writeInt( getFaceDetection() );
        dest.writeInt( getFaceRecognition() );
    }

    protected OV2640Settings(Parcel in) {
        this.setFramesize( in.readInt() );
        this.setQuality( in.readInt() );
        this.setBrightness( in.readInt() );
        this.setContrast( in.readInt() );
        this.setSaturation( in.readInt() );
        this.setEffect( in.readInt() );
        this.setWbMode( in.readInt() );
        this.setAwb( in.readInt() );
        this.setAwbGain( in.readInt() );
        this.setAec( in.readInt() );
        this.setAec2( in.readInt() );
        this.setAeLevel( in.readInt() );
        this.setAecValue( in.readInt() );
        this.setAgc( in.readInt() );
        this.setAgcGain( in.readInt() );
        this.setGainCeiling( in.readInt() );
        this.setBpc( in.readInt() );
        this.setWpc( in.readInt() );
        this.setRawGma( in.readInt() );
        this.setLensCorrection( in.readInt() );
        this.setVFlip( in.readInt() );
        this.setHMirror( in.readInt() );
        this.setDcw( in.readInt() );
        this.setColorBar( in.readInt() );
        this.setFaceDetection( in.readInt() );
        this.setFaceRecognition( in.readInt() );
    }
}
