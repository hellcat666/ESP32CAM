package com.catsoft.esp32cam;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.catsoft.esp32cam.ov2640.OV2640Camera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import static com.catsoft.esp32cam.ov2640.OV2640Constants.CAMERA_NAME;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.NAME;
import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity implements ESP32CameraFragment.OnESP32CameraFragmentInterface {

    private static final String TAG = "MainActivity";

    private static final String ACTIVITY_READY = "activity-ready";
    private static final String COMMUNICATION_READY = "communication-ready";
    private static final String CAMERA_STATUS = "camera-status";
    private static final String STATUS = "status";
    private static final String EXIT = "exit";

    private Context mContext;
    private Intent mIntent;
    private boolean mCommunicationChecked;
    ViewDialogSpinningWheel mSpinningWheelDialog;

    private OV2640Camera mOV2640Camera;

    private ESP32CameraFragment mESP23CameraFragment = null;

    private IntentFilter mAppFilter = null;
    private boolean isRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        mIntent = getIntent();

        initToolBar();
        initFragments();
        initSettings();
        initCamera();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP) {
//            checkCommunication();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    private void initToolBar() {
        if(Build.VERSION.SDK_INT<= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable( new ColorDrawable( ContextCompat.getColor(this, R.color.black ) ) );
        }
        else {
            getSupportActionBar().setBackgroundDrawable( new ColorDrawable( getColor( R.color.black ) ) );
        }
    }

    public boolean OnCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.menu_settings:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
                startSettings();
                break;
            // action with ID action_settings was selected
            case R.id.menu_exit:
                Toast.makeText(this, "Exit selected", Toast.LENGTH_SHORT).show();
                exit();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onESP32CameraFragmentComplete() {

        Log.i(TAG, "onESP32CameraFragmentComplete");
        Intent anIntent = new Intent();
        anIntent.setAction(CAMERA_NAME);
        anIntent.putExtra( NAME, mOV2640Camera.getCameraName() );
        mContext.sendBroadcast( anIntent );
    }

    private void initFragments() {
        mESP23CameraFragment = new ESP32CameraFragment();
        if(mESP23CameraFragment!=null) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.esp32camera, mESP23CameraFragment, "ESP32CAMERA_FRAGMENT").
                    commit();
        }
    }

    private void initSettings() {
        /*
        if(mIntent!=null)
            if(mIntent.getExtras()!=null)
                mAppSettings = (AppSettings)mIntent.getExtras().get(AppSettings.APP_SETTINGS);
         */
    }

    private void initCamera() {

        mOV2640Camera = new OV2640Camera("Camera #1", "192.168.1.11");
//        mOV2640Camera = new OV2640Camera("Camera #2", "192.168.1.12");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
        }
    }

    private void registerReceiver() {
        if(mAppFilter==null) {
            mAppFilter = new IntentFilter();
            mAppFilter.addAction( ACTIVITY_READY );
            mAppFilter.addAction( COMMUNICATION_READY );
            mContext.registerReceiver( mMessageReceiver, mAppFilter );
            isRegistered = true;
        }
    }

    private void unregisterReceiver() {
        if(mAppFilter!=null) {
            mContext.unregisterReceiver(mMessageReceiver);
            mAppFilter = null;
            isRegistered = false;
        }
    }

    private void checkCommunication() {
        Log.i(TAG, "checkCommunication()");
        mCommunicationChecked = false;
        mSpinningWheelDialog = new ViewDialogSpinningWheel();
        mSpinningWheelDialog.setTitle( "Checking Communications" );
        mSpinningWheelDialog.setMessage( "Be sure that WiFi is on ..." );
        mSpinningWheelDialog.showDialog( this );
        new Thread( new Runnable() {

            public void run() {
                while (!CommunicationStatus.isWiFiConnected( mContext )) {
                    try {
                        sleep( 1000 );
                    } catch (InterruptedException e) {
                    }
                }
                mCommunicationChecked = true;
                Intent anIntent = new Intent();
                anIntent.setAction( COMMUNICATION_READY );
                mContext.sendBroadcast( anIntent );
            }
        } ).start();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent == null) {
                return;
            }

            switch (intent.getAction()) {
                case COMMUNICATION_READY:
                    Log.i(TAG, "COMMUNICATION_READY Received ...");
                    mSpinningWheelDialog.hideDialog();
                    break;
            }
        }
    };

    private void startSettings() {
        /*
        Intent anIntent = new Intent(this, AppSettings.class);
        anIntent.putExtra(AppSettings.APP_SETTINGS, mAppSettings);
        anIntent.putExtra("parent", this.getClass());
        this.startActivity(anIntent);
         */
    }

    private void exit() {
        Intent anIntent = new Intent();
        anIntent.setAction(EXIT);
        mContext.sendBroadcast(anIntent);
        finish();
    }

    private void showToast(final String msg){
        //gets the main thread
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                // run this code in the main thread
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void testPing() {

    }
}
