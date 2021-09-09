package com.catsoft.esp32cam;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import static com.catsoft.esp32cam.ov2640.OV2640Constants.CAMERA_REFRESH;


/**
 * Project: ESP32CAM
 * Package: com.catsoft.esp32cam
 * File:
 * Created by HellCat on 05.05.2021.
 */
public class ESP32CameraWebView extends WebView {

    private final static String TAG = "ESP32CameraWebView";

    public static final String DOCUMENT_READY = "document-ready";
    public static final String ON_LOAD = "on-load";
    public static final String SERVER_URL = "server-url";
    public static final String CAMERA_STATUS = "camera-status";
    public static final String CAMERA_STATE = "camera-state";
    public static final String STATUS = "status";
    public static final String SHOW_SETTINGS = "show-settings";

    private static final String HTML_BLANK = "about:blank";
    private static final String HTML_CAMERA_FILE = "file:///android_asset/HTMLVideoCamera.html";
    private static final String HTML_NO_CAMERA_FILE = "file:///android_asset/HTMLNoCamera.html";

    private String mCameraStreamUrl = "";
    public String getCameraStreamUrl() { return mCameraStreamUrl; }
    public void setCameraStreamUrl(String cameraStreamUrl) { this.mCameraStreamUrl = cameraStreamUrl; }
    private boolean mCameraOnline = false;

    static private Context context;
    private IntentFilter intentFilter = null;
    boolean done = false;

    public ESP32CameraWebView(Context context, AttributeSet attrs) {
        super(context);
        Log.i(TAG, "ESP32CameraWebView(...)");
        this.context = this.getContext();
        initialize();
        registerReceiver();
    }

    private void initialize() {
        Log.i(TAG, "initView()");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        loadUrl( HTML_BLANK );
        getSettings().setJavaScriptEnabled(true);
//        getSettings().setDomStorageEnabled(true);
        addJavascriptInterface(new WebAppInterface(context), "Android");
//        getSettings().setAllowFileAccess(true);
//        getSettings().setAppCacheEnabled( true );
//        getSettings().setAllowUniversalAccessFromFileURLs(true);
//        getSettings().setLoadsImagesAutomatically(true);
//        getSettings().setBuiltInZoomControls(true);
//        getSettings().setDisplayZoomControls(false);
//        getSettings().setLoadWithOverviewMode(true);
        getSettings().setUseWideViewPort(true);
        setBackgroundColor( 0x00000000 );
        setLayerType( WebView.LAYER_TYPE_SOFTWARE, null );
        clearCache( true );
        setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "I've been CLICKED !!!");
                Intent anIntent = new Intent();
                anIntent.setAction(SHOW_SETTINGS);
                context.sendBroadcast( anIntent );
                return true;
            }
        });
    }

    private void registerReceiver() {
        Log.i(TAG, "registerReceiver()");
        intentFilter = new IntentFilter();
        intentFilter.addAction(DOCUMENT_READY);
        intentFilter.addAction(ON_LOAD);
        intentFilter.addAction(CAMERA_STATUS);
        intentFilter.addAction(CAMERA_REFRESH);
        context.registerReceiver(messageReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        Log.i(TAG, "unregisterReceiver()");
        context.unregisterReceiver(messageReceiver);
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume()");
        super.onResume();
        registerReceiver();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause()");
        super.onPause();
        done = false;
        unregisterReceiver();
    }

    public void onDestroy() {
        Log.i(TAG, "onDestroy()");
        super.onPause();
        done = false;
        unregisterReceiver();
    }

    private void setCamera(boolean status) {
        mCameraOnline = status;
        try { Thread.sleep( 100 ); }
        catch(InterruptedException iex) { iex.printStackTrace();}
        if(mCameraOnline) {
            done = false;
            loadUrl( HTML_CAMERA_FILE+"?url="+mCameraStreamUrl );
        }
        else {
            loadUrl( HTML_NO_CAMERA_FILE );
        }
    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent==null) return;

            switch (intent.getAction()) {
                case DOCUMENT_READY:
                    Log.i( TAG, "DOCUMENT_READY Received ..." );
                    if ((!done) && (!mCameraStreamUrl.isEmpty())) {
                        setCameraVideoStreamUrl( mCameraStreamUrl );
                        done = true;
                    }
                    break;
                case CAMERA_REFRESH:
                    Log.i( TAG, "CAMERA_STATUS Received ..." );
                    mCameraStreamUrl = intent.getExtras().getString( SERVER_URL ) + "/mjpeg/1";
                    boolean status = intent.getExtras().getBoolean( STATUS );
                    if( (mCameraOnline != status) || (done==false)) { setCamera( status ); }
                    break;
            }
        }
    };

    /**
     * setCameraVideoStreamUrl
     * @param srvUrl
     *
     * Set the url of the Camera, calling JavaScript function setServerUrl(...)
     */
    private void setCameraVideoStreamUrl(String srvUrl) {
        Log.i(TAG, "setCameraVideoStreamUrl(...)");
        evaluateJavascript("javascript:setCameraStreamUrl('" + srvUrl + "')", null);
    }
    /**
     * The WebInterface allows the HTML Document of the WebView
     * to communicate with Android native
     */
    public class WebAppInterface {
        Context context;

        /** Instantiate the interface and set the context
         * @param ctx
         **/
        WebAppInterface(Context ctx) {
            context = ctx;
        }

        /** Sent when the HTML Document is ready */
        @JavascriptInterface
        public void onDocumentReady() {
            Log.i(TAG, "WebAppInterface.onDocumentReady()");
            Intent anIntent = new Intent();
            anIntent.setAction(DOCUMENT_READY);
            context.sendBroadcast(anIntent);
        }
        /** Sent during HTML Body onLoad(), returning the current Server Url stored in localStorage */
        @JavascriptInterface
        public void onLoad() {
            Log.i(TAG, "WebAppInterface.onLoad(...)");
//            setCameraServerUrl( cameraServerUrl );
            /*
            Intent anIntent = new Intent();
            anIntent.setAction(ON_LOAD);
            anIntent.putExtra(SERVER_URL,srvUrl);
            context.sendBroadcast(anIntent);
            */
        }

        /** Send the current Status of the Camera */
        @JavascriptInterface
        public void onCameraStatus(boolean status) {
            Log.i(TAG, "WebAppInterface.onCameraStatus(...)");
            Intent anIntent = new Intent();
            anIntent.setAction(CAMERA_STATUS);
            anIntent.putExtra(STATUS, status);
            context.sendBroadcast(anIntent);
        }

        /** Send the current Status of the Camera */
        @JavascriptInterface
        public void onCameraResponse(String response) {
            Log.i(TAG, "WebAppInterface.onCameraStatus(" + response + ")");
            /*
            Intent anIntent = new Intent();
            anIntent.setAction(CAMERA_RESPONSE);
            anIntent.putExtra(RESPONSE, response);
            context.sendBroadcast(anIntent);
             */
        }
    }

}
