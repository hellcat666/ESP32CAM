package com.catsoft.esp32cam.ov2640;

/**
 * Project: ESP32CAM
 * Package: com.catsoft.esp32cam.ov2640
 * File:
 * Created by HellCat on 17.06.2021.
 */
public abstract class OV2640Constants {

    // Global Constants
    static final String HTTP = "http://";
    static final String MJPEG = "/mjpeg/1";

    // BroadcastManager Intent Constants (Action, Extras, etc ...)
    public static final String CAMERA_NAME = "camera-name";
    public static final String CAMERA_STATUS = "camera-status";
    public static final String CAMERA_REFRESH = "camera-refresh";
    public static final String CAMERA_INFO = "camera-info";
    public static final String SERVER_URL = "server-url";
    public final static String NAME = "name";
    public final static String STATUS = "status";

    public static final String SETTINGS_RECEIVED = "settings-received";
    public static final String SETTINGS_READY = "settings-ready";
    public static final String REQUEST_SETTINGS = "request-settings";
    public static final String SEND_SETTINGS = "send-settings";
    public static final String CAMERA_SETTINGS = "camera-settings";
    public static final String SET_SETTING = "set-setting";
    public static final String SAVE_SETTING = "save-setting";
    public static final String SETTINGS = "settings";
    public static final String SETTING = "setting";

    public static final String SEND_COMMAND = "send-command";
    public static final String COMMAND = "command";
    public static final String PARAMETER = "parameter";
    public static final String COMMAND_STATUS = "command-status";
    public static final String REBOOT = "reboot";



    // OV2640  Camera Parameters
    public final static String FRAMESIZE = "framesize";
    public final static String QUALITY = "quality";
    public final static String BRIGHTNESS= "brightness";
    public final static String CONTRAST = "contrast";
    public final static String SATURATION = "saturation";
    public final static String EFFECT = "special_effect";
    public final static String AWB = "awb";
    public final static String AWB_GAIN = "awb_gain";
    public final static String WBMODE = "wb_mode";
    public final static String AEC = "aec";
    public final static String AEC2 = "aec2";                       // AKA AEC_DSP
    public final static String AE_LEVEL = "ae_level";
    public final static String AEC_VALUE = "aec_value";
    public final static String AGC = "agc";
    public final static String AGC_GAIN = "agc_gain";
    public final static String GAIN_CEILING = "gainceiling";
    public final static String BPC = "bpc";
    public final static String WPC = "wpc";
    public final static String RAW_GMA = "raw_gma";
    public final static String LENS_CORRECTION = "lenc";
    public final static String H_MIRROR = "hmirror";
    public final static String V_FLIP = "vflip";
    public final static String DCW = "dcw";
    public final static String COLOR_BAR = "colorbar";
    public final static String FACE_ENROLL = "face_enroll";
    public final static String FACE_DETECTION = "face_detect";
    public final static String FACE_RECOGNITION = "face_recognize";
    public final static String VALUE = "value";


}