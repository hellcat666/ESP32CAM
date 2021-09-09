package com.catsoft.esp32cam;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * Project: ESP32CAM
 * Package: com.catsoft.esp32cam
 * File:
 * Created by HellCat on 11.05.2021.
 */
public class SettingsDialogFragment extends DialogFragment {

    static final String TAG = "SettingsDialogFragment";

    static final String REQUEST_SETTINGS = "request-settings";
    static final String SAVE_SETTINGS = "save-settings";
    static final String SETTINGS = "settings";

    Context mContext;
    boolean mVisible;

    public SettingsDialogFragment() {
        super();
        Log.i(TAG, "SettingsDialogFragment()");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateDialog()");
        mVisible = false;
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_fire_missiles)
                .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        Intent anIntent = new Intent();
        anIntent.setAction(REQUEST_SETTINGS);
        mContext.sendBroadcast( anIntent );
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show( manager, tag );
        mVisible = true;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mVisible = false;
    }

    public boolean isDialogVisible() {
        return mVisible;
    }
}
