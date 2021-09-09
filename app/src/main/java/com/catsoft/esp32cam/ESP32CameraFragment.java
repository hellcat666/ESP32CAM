package com.catsoft.esp32cam;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.catsoft.esp32cam.ov2640.OV2640Camera;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Project: ESP32CAM
 * Package: com.catsoft.esp32cam
 * File:
 * Created by HellCat on 05.05.2021.
 */
public class ESP32CameraFragment extends Fragment {


    private final static String TAG = "ESP32CameraFragment";

    private static View mView;
    static private Context mContext;

    OV2640Camera mCamera = null;

    private ESP32CameraStatusBar mCameraStatusBar;
    private ESP32CameraWebView mCameraWebView;
    private ESP32CameraSettingsDialog mSettingsDialog;

    private boolean mCameraOnLine = false;

    private IntentFilter mAppFilter = null;

    public interface OnESP32CameraFragmentInterface {
        void onESP32CameraFragmentComplete();
    }

    OnESP32CameraFragmentInterface mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        mContext = this.getContext();
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");
        mContext = container.getContext();
        mView = inflater.inflate(R.layout.frag_esp32_camera, container, false);
        mView.setOnTouchListener( new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(mSettingsDialog!=null) {
                    mSettingsDialog.show();
                }
                else {
                    mSettingsDialog = new ESP32CameraSettingsDialog(getActivity());
                    mSettingsDialog.show();
                }
                return false;
            }
        } );

        mCameraStatusBar = new ESP32CameraStatusBar( mView );
        mCameraWebView = (ESP32CameraWebView) mView.findViewById(R.id.cameraWebView);
        mSettingsDialog = new ESP32CameraSettingsDialog(getActivity());

        return mView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated()");
        super.onActivityCreated(savedInstanceState);

        mCameraOnLine = false;

        mListener.onESP32CameraFragmentComplete();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.i(TAG, "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach()");
        super.onAttach(context);
        mContext = context;

        try {
            mListener = (OnESP32CameraFragmentInterface)mContext;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnESP32CameraFragmentInterface");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    /*
    public void onESP32CameraFragmentComplete() {

        Log.i(TAG, "onESP32CameraFragmentComplete");
        Intent anIntent = new Intent();
        anIntent.setAction(CAMERA_NAME);
        anIntent.putExtra( NAME, mCamera.getCameraName() );
        mContext.sendBroadcast( anIntent );

    }
    */
}
