package com.catsoft.esp32cam;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;


public class CommunicationStatus {

    static Context context;
    static ConnectivityManager connectivityManager;
    static boolean wifiActive;
    static boolean wifiConnected;
    static boolean mobileActive;
    static boolean mobileConnected;
    static boolean bluetoothActive;
    static boolean bluetoothConnected;

    private static void checkInternetConnection(Context context) {

        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        for (Network network : connectivityManager.getAllNetworks()) {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
            if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                wifiActive = true;
                if(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
                    wifiConnected = true;
                else
                    wifiConnected = false;
            }
            else {
                wifiActive = false;
                wifiConnected = false;
            }
            if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                mobileActive = true;
                if(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
                    mobileConnected = true;
                else
                    mobileConnected = false;
            }
            else {
                mobileActive = false;
                mobileConnected = false;
            }
            if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)) {
                bluetoothActive = true;
                if(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
                    bluetoothConnected = true;
                else
                    bluetoothConnected = false;
            }
            else {
                bluetoothActive = false;
                bluetoothConnected = false;
            }
        }
    }

    static public boolean isWiFiActive(Context context) {
        checkInternetConnection(context);
        return wifiActive;
    }

    static public boolean isWiFiConnected(Context context) {
        checkInternetConnection(context);
        return wifiConnected;
    }

    static public boolean isMobileActive() {
        checkInternetConnection(context);
        return mobileActive;
    }

    static public boolean isMobileConnected() {
        checkInternetConnection(context);
        return mobileConnected;
    }

    static public boolean isBluetoothActive() {
        checkInternetConnection(context);
        return bluetoothActive;
    }

    static public boolean isBluetoothConnected() {
        checkInternetConnection(context);
        return bluetoothConnected;
    }


}