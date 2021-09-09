package com.catsoft.esp32cam;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;

import com.catsoft.esp32cam.ov2640.OV2640Settings;

import static com.catsoft.esp32cam.ov2640.OV2640Constants.AEC;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.AEC2;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.AEC_VALUE;
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
import static com.catsoft.esp32cam.ov2640.OV2640Constants.H_MIRROR;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.LENS_CORRECTION;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.QUALITY;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.RAW_GMA;
import static com.catsoft.esp32cam.ov2640.OV2640Constants.REBOOT;
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
 * Project: ESP32CAM
 * Package: com.catsoft.esp32cam
 * File:
 * Created by HellCat on 22.06.2021.
 */
public class ESP32CameraSettingsDialog {

    public final static String TAG = "ESP32CameraSettingsDlg";

    // Defined Array values to show in ListView
    String[] resolutions = new String[] {
            "QQVGA (160x120)",
            "HQVGA (240x176)",
            "QVGA (320x240)",
            "CIF (400x296)",
            "VGA (640x480)",
            "SVGA (800x600)",
            "XGA (1024x768)",
            "SXGA (1280x1024)",
            "UXGA (1600x1200)"
    };
    Spinner lstResolutions;
    ArrayAdapter adapterResolutions;

    SeekBar sbQuality;

    SeekBar sbBrightness;

    SeekBar sbContrast;

    SeekBar sbSaturation;

    String[] effects = new String[] {
            "No Effect",
            "Negative",
            "Grayscale",
            "Red Tint",
            "Green Tint",
            "Blue Tint",
            "Sepia"
    };
    Spinner lstEffects;
    ArrayAdapter adapterEffects;

    Switch swAWB;

    Switch swAWBGain;

    LinearLayout llWBMode;
    String[] wbModes = new String[] { "Auto",
            "Sunny",
            "Cloudy",
            "Office",
            "Home"
    };
    Spinner lstWBMode;
    ArrayAdapter adapterWBModes;

    Switch swAEC;

    Switch swAECDsp;

    SeekBar sbAELevel;

    LinearLayout llExposure;
    SeekBar sbExposure;

    Switch swAGC;

    LinearLayout llAgcGain;
    SeekBar sbAgcGain;

    LinearLayout llGainCeiling;
    SeekBar sbGainCeiling;

    Switch swBPC;

    Switch swWPC;

    Switch swRawGMA;

    Switch swLensCorrection;

    Switch swHMirror;

    Switch swVFlip;

    Switch swDCW;

    Switch swColorBar;

    Switch swFaceDetection;

    Switch swFaceRecognition;

    Activity mParent;
    Context mContext;
    Dialog mDialog;
    Rect mDisplayRectangle;
    Window mWindow;
    boolean mVisible;

    IntentFilter mIntentFilter;

    OV2640Settings mOV2640Settings;

    boolean mDialogInitialized;

    public ESP32CameraSettingsDialog(Activity activity) {
        mParent = activity;
        mContext = ESP32Camera.getContext();
        mDialogInitialized = false;
        mDisplayRectangle = new Rect();
        mWindow = mParent.getWindow();
        mWindow.getDecorView().getWindowVisibleDisplayFrame( mDisplayRectangle);
        AlertDialog.Builder builder = new AlertDialog.Builder(mParent, R.style.loading_dialog_style );
        builder.setTitle( "" );
        View dlgLayout = mParent.getLayoutInflater().inflate( R.layout.ov2640_settings_dlg, null );
        final ScrollView scrollView = new ScrollView(mContext);
        scrollView.addView(dlgLayout);
        builder.setView( scrollView);

        lstResolutions = (Spinner)dlgLayout.findViewById( R.id.lstResolutions );
        adapterResolutions = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, resolutions);
        lstResolutions.setAdapter( adapterResolutions );
        lstResolutions.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mOV2640Settings!=null) mOV2640Settings.setFramesize(position);
                if((mDialog!=null) && (mDialog.isShowing()))
                    setSetting(FRAMESIZE, String.valueOf( position ));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        } );

        sbQuality = (SeekBar)dlgLayout.findViewById( R.id.sbQuality );
        sbQuality.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mOV2640Settings != null) mOV2640Settings.setQuality( progress );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting( QUALITY, String.valueOf( progress ) );
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        sbBrightness = (SeekBar)dlgLayout.findViewById( R.id.sbBrightness );
        sbBrightness.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mOV2640Settings!=null) mOV2640Settings.setBrightness( progress );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(BRIGHTNESS, String.valueOf( progress ));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        sbContrast = (SeekBar)dlgLayout.findViewById( R.id.sbContrast );
        sbContrast.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mOV2640Settings!=null) mOV2640Settings.setContrast( progress );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(CONTRAST, String.valueOf( progress ));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        sbSaturation = (SeekBar)dlgLayout.findViewById( R.id.sbSaturation );
        sbSaturation.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mOV2640Settings!=null) mOV2640Settings.setSaturation( progress );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(SATURATION, String.valueOf( progress ));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        lstEffects = (Spinner)dlgLayout.findViewById( R.id.lstSpecialEffects );
        adapterEffects = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, effects);
        lstEffects.setAdapter(adapterEffects);
        lstEffects.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mOV2640Settings!=null) mOV2640Settings.setEffect( position );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(EFFECT, String.valueOf( position ));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        swAWB = (Switch) dlgLayout.findViewById( R.id.swAWB );
        swAWB.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mOV2640Settings!=null) mOV2640Settings.setAwb( isChecked ? 1 : 0 );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(AWB, isChecked ? "1" : "0");
            }
        });

        swAWBGain = (Switch) dlgLayout.findViewById( R.id.swAWBGain );
        swAWBGain.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mOV2640Settings!=null) mOV2640Settings.setAwbGain( isChecked ? 1 : 0 );
                showWBMode( isChecked );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(AWB_GAIN, isChecked ? "1" : "0");
            }
        });
        llWBMode = (LinearLayout) dlgLayout.findViewById(R.id.llWBMode);
        lstWBMode = (Spinner)dlgLayout.findViewById( R.id.lstWBModes );
        adapterWBModes = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, wbModes);
        lstWBMode.setAdapter(adapterWBModes);
        lstWBMode.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mOV2640Settings!=null) mOV2640Settings.setWbMode( position );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(WBMODE, String.valueOf( position ));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        swAEC = (Switch)dlgLayout.findViewById( R.id.swAEC );
        swAEC.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mOV2640Settings!=null) mOV2640Settings.setAec( isChecked ? 1 : 0);
                showExposure( !isChecked );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(AEC, isChecked ? "1" : "0");
            }
        });

        swAECDsp = (Switch)dlgLayout.findViewById( R.id.swAECDSP );
        swAECDsp.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mOV2640Settings!=null) mOV2640Settings.setAec2( isChecked ? 1 : 0 );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(AEC2, isChecked ? "1" : "0");
            }
        });

        sbAELevel = (SeekBar)dlgLayout.findViewById( R.id.sbAELevel );
        sbAELevel.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mOV2640Settings!=null) mOV2640Settings.setAeLevel( progress );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(AE_LEVEL, String.valueOf( progress ));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        llExposure = (LinearLayout) dlgLayout.findViewById( R.id.llExposure);
        sbExposure = (SeekBar) dlgLayout.findViewById( R.id.sbExposure );
        sbExposure.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mOV2640Settings!=null) mOV2640Settings.setAecValue( progress );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(AEC_VALUE, String.valueOf( progress ));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        swAGC = (Switch)dlgLayout.findViewById( R.id.swAGC );
        swAGC.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mOV2640Settings!=null) mOV2640Settings.setAgc( isChecked ? 1 : 0 );
                showAgcGain( !isChecked );
                showGainCeiling( isChecked );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(AGC, isChecked ? "1" : "0");
            }
        });

        llAgcGain = (LinearLayout) dlgLayout.findViewById( R.id.llAgcGain );
        sbAgcGain = (SeekBar) dlgLayout.findViewById( R.id.sbGain );
        sbAgcGain.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mOV2640Settings!=null) mOV2640Settings.setAgcGain( progress );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(AGC_GAIN, String.valueOf( progress ));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        llGainCeiling = (LinearLayout) dlgLayout.findViewById( R.id.llGainCeiling );
        sbGainCeiling = (SeekBar)dlgLayout.findViewById( R.id.sbGainCeiling );
        sbGainCeiling.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mOV2640Settings!=null) mOV2640Settings.setGainCeiling( progress );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(GAIN_CEILING, String.valueOf( progress ));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        swBPC = (Switch)dlgLayout.findViewById( R.id.swBPC );
        swBPC.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mOV2640Settings!=null) mOV2640Settings.setBpc( isChecked ? 1 : 0);
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(BPC, isChecked ? "1" : "0" );
            }
        });

        swWPC = (Switch)dlgLayout.findViewById( R.id.swWPC );
        swWPC.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mOV2640Settings!=null) mOV2640Settings.setWpc( isChecked ? 1 : 0 );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(WPC, isChecked ? "1" : "0" );
            }
        });

        swRawGMA = (Switch)dlgLayout.findViewById( R.id.swRawGMA );
        swRawGMA.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mOV2640Settings!=null) mOV2640Settings.setRawGma( isChecked ? 1 : 0 );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(RAW_GMA, isChecked ? "1" : "0" );
            }
        });

        swLensCorrection = (Switch)dlgLayout.findViewById( R.id.swLensCorrection );
        swLensCorrection.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mOV2640Settings!=null) mOV2640Settings.setLensCorrection( isChecked ? 1 : 0 );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(LENS_CORRECTION, isChecked ? "1" : "0" );
            }
        });

        // Due to the 90° Rotation of the Camera Image, HMirror is performed using VFlip
        swHMirror = (Switch)dlgLayout.findViewById( R.id.swHMirror );
        swHMirror.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mOV2640Settings.setHMirror( isChecked ? 1 : 0 );
//                setSetting(H_MIRROR, isChecked ? "1" : "0" );
                if(mOV2640Settings!=null) mOV2640Settings.setVFlip( isChecked ? 1 : 0 );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(V_FLIP, isChecked ? "1" : "0" );
            }
        });

        // Due to the 90° Rotation of the Camera Image, VFlip is performed using HMirror
        swVFlip = (Switch)dlgLayout.findViewById( R.id.swVFlip );
        swVFlip.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mOV2640Settings.setVFlip( isChecked ? 1 : 0 );
//                setSetting(V_FLIP, isChecked ? "1" : "0" );
                if(mOV2640Settings!=null) mOV2640Settings.setHMirror( isChecked ? 1 : 0 );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(H_MIRROR, isChecked ? "1" : "0" );
            }
        });

        swDCW = (Switch)dlgLayout.findViewById( R.id.swDCW );
        swDCW.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mOV2640Settings!=null) mOV2640Settings.setDcw( isChecked ? 1 : 0 );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(DCW, isChecked ? "1" : "0" );
            }
        });

        swColorBar = (Switch)dlgLayout.findViewById( R.id.swColorBar );
        swColorBar.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mOV2640Settings!=null) mOV2640Settings.setColorBar( isChecked ? 1 : 0 );
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(COLOR_BAR, isChecked ? "1" : "0");
            }
        });

        swFaceDetection = (Switch)dlgLayout.findViewById( R.id.swFaceDetection );
        swFaceDetection.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mOV2640Settings!=null) mOV2640Settings.setFaceDetection( isChecked ? 1 : 0);
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(FACE_DETECTION, isChecked ? "1" : "0");
            }
        });

        swFaceRecognition = (Switch)dlgLayout.findViewById( R.id.swFaceRecognition );
        swFaceRecognition.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mOV2640Settings!=null) mOV2640Settings.setFaceRecognition( isChecked ? 1 : 0);
                if((mDialog!=null) && (mDialog.isShowing())) setSetting(FACE_RECOGNITION, isChecked ? "1" : "0");
            }
        } );

        builder.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do your action
                mDialog.hide();

            }
        });

        builder.setNegativeButton( "REBOOT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "Rebooting Camera ...");
                Intent anIntent = new Intent();
                anIntent.setAction(REBOOT);
                mContext.sendBroadcast( anIntent );
            }
        });

        mDialog = builder.create();
        mDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        mDialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
        mDialogInitialized = false;
        registerReceiver();
    }

    private void registerReceiver() {
        mIntentFilter = new IntentFilter( );
        mIntentFilter.addAction( SETTINGS_RECEIVED);
        mIntentFilter.addAction( CAMERA_SETTINGS);
        mIntentFilter.addAction(CAMERA_STATUS);
        mIntentFilter.addAction(COMMAND_STATUS);
        mContext.registerReceiver(messageReceiver, mIntentFilter);
    }

    private void unregisterReceiver() {
        mContext.unregisterReceiver( messageReceiver );
    }

    private void setSetting(String command, String value) {
        Intent anIntent = new Intent();
        anIntent.setAction( SET_SETTING );
        anIntent.putExtra( SETTING, command);
        anIntent.putExtra( VALUE,value );
        mContext.sendBroadcast( anIntent );
    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent==null) return;

            switch(intent.getAction()) {
                case SETTINGS_RECEIVED:
                    Log.i(TAG, intent.getExtras().getString( SETTINGS ));
                    Intent anIntent = new Intent();
                    anIntent.setAction(REQUEST_SETTINGS);
                    mContext.sendBroadcast( anIntent );
                    break;
                case CAMERA_SETTINGS:
                    Log.i(TAG, "CAMERA_SETTINGS Received");
                    mOV2640Settings = intent.getExtras().getParcelable( SETTINGS );
                    refresh();
                    break;
                case COMMAND_STATUS:
                    boolean status = intent.getExtras().getBoolean( STATUS );
                    Log.i(TAG, String.format( "HTTP Request: %s", (status==true) ? "SUCCESS" : "ERROR"));
                    break;
            }
        }
    };

    private void refresh() {
        if(mOV2640Settings!=null) {
            lstResolutions.setSelection( mOV2640Settings.getFramesize() );

            sbQuality.setProgress( mOV2640Settings.getQuality());
            sbBrightness.setProgress( mOV2640Settings.getBrightness());
            sbContrast.setProgress( mOV2640Settings.getContrast());
            sbSaturation.setProgress( mOV2640Settings.getSaturation() );

            lstEffects.setSelection( mOV2640Settings.getEffect() );

            swAWB.setChecked( mOV2640Settings.getAwb()==1 ? true : false );
            swAWBGain.setChecked( mOV2640Settings.getAwb()==1 ? true : false );
            showWBMode(swAWBGain.isChecked());
            lstWBMode.setSelection( mOV2640Settings.getWbMode() );

            swAEC.setChecked( mOV2640Settings.getAec()==1 ? true : false );
            showExposure( !swAEC.isChecked() );
            swAECDsp.setChecked( mOV2640Settings.getAec2()==1 ? true : false );
            sbAELevel.setProgress( mOV2640Settings.getAeLevel() );
            sbExposure.setProgress(mOV2640Settings.getAecValue() );

            swAGC.setChecked( mOV2640Settings.getAgc()==1 ? true : false );
            showAgcGain(!swAGC.isChecked());
            sbAgcGain.setProgress( mOV2640Settings.getAgcGain() );
            showGainCeiling(swAGC.isChecked());
            sbGainCeiling.setProgress( mOV2640Settings.getGainCeiling() );


            swBPC.setChecked( mOV2640Settings.getBpc()==1 ? true : false );
            swWPC.setChecked( mOV2640Settings.getWpc()==1 ? true : false );
            swRawGMA.setChecked( mOV2640Settings.getRawGma()==1 ? true : false );

            swLensCorrection.setChecked( mOV2640Settings.getLensCorrection()==1 ? true : false );

            swHMirror.setChecked( mOV2640Settings.getHMirror()==1 ? true : false );
            swVFlip.setChecked( mOV2640Settings.getVFlip()==1 ? true : false );

            swDCW.setChecked( mOV2640Settings.getDcw()==1 ? true : false );

            swColorBar.setChecked( mOV2640Settings.getColorBar()==1 ? true : false );

            swFaceDetection.setChecked( mOV2640Settings.getFaceDetection()==1 ? true : false );
            swFaceRecognition.setChecked( mOV2640Settings.getFaceRecognition()==1 ? true : false );
        }
    }

    private void showWBMode(boolean visible) {
        if (visible==true)
            llWBMode.setVisibility( View.VISIBLE );
        else
            llWBMode.setVisibility( View.GONE );
    }

    private void showExposure(boolean visible) {
        if(visible==true)
            llExposure.setVisibility( View.VISIBLE );
        else
            llExposure.setVisibility( View.GONE );
    }

    private void showAgcGain(boolean visible) {
        if(visible==true)
            llAgcGain.setVisibility( View.VISIBLE );
        else
            llAgcGain.setVisibility( View.GONE );
    }

    private void showGainCeiling(boolean visible) {
        if(visible==true)
            llGainCeiling.setVisibility( View.VISIBLE );
        else
            llGainCeiling.setVisibility( View.GONE );
    }

    public void show() {
        mDialog.show();
        mDialog.getWindow().setLayout((int)(mDisplayRectangle.width() * 0.95f), ((int)(mDisplayRectangle.height() * 0.75f)));
        mDialog.getWindow().setGravity( Gravity.CENTER_VERTICAL + Gravity.CENTER_HORIZONTAL );

    }
}
