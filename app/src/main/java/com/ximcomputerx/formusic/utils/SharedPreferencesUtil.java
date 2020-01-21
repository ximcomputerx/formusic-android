package com.ximcomputerx.formusic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.ximcomputerx.formusic.application.ForMusicApplication;

/**
 * @AUTHOR HACKER
 */
public class SharedPreferencesUtil {
    private final static int PREFERENCES_MODE = Context.MODE_PRIVATE;
    private final static int INT_PREFERENCES_DEFAULT_VALUE = 0;
    private final static long LONG_PREFERENCES_DEFAULT_VALUE = 0l;
    private final static float FLOAT_PREFERENCES_DEFAULT_VALUE = 0f;
    private final static boolean BOOLEAN_PREFERENCES_DEFAULT_VALUE = false;
    private final static String STRING_PREFERENCES_DEFAULT_VALUE = "";

    /**
     * @param preName
     * @param mode
     * @param key
     * @param value
     */
    public static boolean setIntPreferences(String preName, int mode, String key,
                                            int value) {
        SharedPreferences preferences = ForMusicApplication.getInstance()
                .getSharedPreferences(preName, mode);
        boolean b = preferences.edit().putInt(key, value).commit();
        return b;
    }

    /**
     * @param preName
     * @param key
     * @param value
     */
    public static boolean setIntPreferences(String preName, String key, int value) {
        return setIntPreferences(preName, PREFERENCES_MODE, key, value);
    }

    /**
     * @param preName
     * @param mode
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getIntPreferences(String preName, int mode, String key,
                                        int defaultValue) {
        SharedPreferences preferences = ForMusicApplication.getInstance()
                .getSharedPreferences(preName, mode);
        return preferences.getInt(key, defaultValue);
    }

    /**
     * @param preName
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getIntPreferences(String preName, String key,
                                        int defaultValue) {
        return getIntPreferences(preName, PREFERENCES_MODE, key, defaultValue);
    }

    /**
     * @param preName
     * @param key
     * @return
     */
    public static int getIntPreferences(String preName, String key) {
        return getIntPreferences(preName, key, INT_PREFERENCES_DEFAULT_VALUE);
    }

    /**
     * @param preName
     * @param mode
     * @param key
     * @param value
     */
    public static void setLongPreferences(String preName, int mode, String key,
                                          long value) {
        SharedPreferences preferences = ForMusicApplication.getInstance()
                .getSharedPreferences(preName, mode);
        preferences.edit().putLong(key, value).commit();
    }

    /**
     * @param preName
     * @param key
     * @param value
     */
    public static void setLongPreferences(String preName, String key, long value) {
        setLongPreferences(preName, PREFERENCES_MODE, key, value);
    }

    /**
     * @param preName
     * @param mode
     * @param key
     * @param defaultValue
     * @return
     */
    public static long getLongPreferences(String preName, int mode, String key,
                                          long defaultValue) {
        SharedPreferences preferences = ForMusicApplication.getInstance()
                .getSharedPreferences(preName, mode);
        return preferences.getLong(key, defaultValue);
    }

    /**
     * @param preName
     * @param key
     * @param defaultValue
     * @return
     */
    public static long getLongPreferences(String preName, String key,
                                          long defaultValue) {
        return getLongPreferences(preName, PREFERENCES_MODE, key, defaultValue);
    }

    /**
     * @param preName
     * @param key
     * @return
     */
    public static long getLongPreferences(String preName, String key) {
        return getLongPreferences(preName, key, LONG_PREFERENCES_DEFAULT_VALUE);
    }

    /**
     * @param preName
     * @param mode
     * @param key
     * @param value
     */
    public static void setBooleanPreferences(String preName, int mode,
                                             String key, boolean value) {
        SharedPreferences preferences = ForMusicApplication.getInstance()
                .getSharedPreferences(preName, mode);
        preferences.edit().putBoolean(key, value).commit();
    }

    /**
     * @param preName
     * @param key
     * @param value
     */
    public static void setBooleanPreferences(String preName, String key,
                                             boolean value) {

        setBooleanPreferences(preName, PREFERENCES_MODE, key, value);
    }

    /**
     * @param preName
     * @param mode
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBooleanPreferences(String preName, int mode,
                                                String key, boolean defaultValue) {
        SharedPreferences preferences = ForMusicApplication.getInstance()
                .getSharedPreferences(preName, mode);
        return preferences.getBoolean(key, defaultValue);
    }

    /**
     * @param preName
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBooleanPreferences(String preName, String key,
                                                boolean defaultValue) {
        return getBooleanPreferences(preName, PREFERENCES_MODE, key, defaultValue);
    }

    /**
     * @param preName
     * @param key
     * @return
     */
    public static boolean getBooleanPreferences(String preName, String key) {
        return getBooleanPreferences(preName, key, BOOLEAN_PREFERENCES_DEFAULT_VALUE);
    }

    /**
     * @param preName
     * @param mode
     * @param key
     * @param value
     */
    public static void setFloatPreferences(String preName, int mode,
                                           String key, float value) {
        SharedPreferences preferences = ForMusicApplication.getInstance()
                .getSharedPreferences(preName, mode);
        preferences.edit().putFloat(key, value).commit();
    }

    /**
     * @param preName
     * @param key
     * @param value
     */
    public static void setFloatPreferences(String preName, String key,
                                           float value) {
        setFloatPreferences(preName, PREFERENCES_MODE, key, value);
    }

    /**
     * @param preName
     * @param mode
     * @param key
     * @param defaultValue
     * @return
     */
    public static float getFloatPreferences(String preName, int mode,
                                            String key, float defaultValue) {
        SharedPreferences preferences = ForMusicApplication.getInstance()
                .getSharedPreferences(preName, mode);
        return preferences.getFloat(key, defaultValue);
    }

    /**
     * @param preName
     * @param key
     * @param defaultValue
     * @return
     */
    public static float getFloatPreferences(String preName, String key,
                                            float defaultValue) {
        return getFloatPreferences(preName, PREFERENCES_MODE, key, defaultValue);
    }

    /**
     * @param preName
     * @param key
     * @return
     */
    public static float getFloatPreferences(String preName, String key) {

        return getFloatPreferences(preName, key,
                FLOAT_PREFERENCES_DEFAULT_VALUE);
    }

    /**
     * @param preName
     * @param mode
     * @param key
     * @param value
     */
    public static void setStringPreferences(String preName, int mode,
                                            String key, String value) {
        SharedPreferences preferences = ForMusicApplication.getInstance()
                .getSharedPreferences(preName, mode);
        preferences.edit().putString(key, value).commit();
    }

    /**
     * @param preName
     * @param key
     * @param value
     */
    public static void setStringPreferences(String preName, String key,
                                            String value) {
        setStringPreferences(preName, PREFERENCES_MODE, key, value);
    }

    /**
     * @param preName
     * @param mode
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getStringPreferences(String preName, int mode,
                                              String key, String defaultValue) {
        SharedPreferences preferences = ForMusicApplication.getInstance()
                .getSharedPreferences(preName, mode);
        return preferences.getString(key, defaultValue);
    }

    /**
     * @param preName
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getStringPreferences(String preName, String key,
                                              String defaultValue) {
        return getStringPreferences(preName, PREFERENCES_MODE, key,
                defaultValue);
    }

    /**
     * @param preName
     * @param key
     * @return
     */
    public static String getStringPreferences(String preName, String key) {
        return getStringPreferences(preName, key, STRING_PREFERENCES_DEFAULT_VALUE);
    }

    /**
     * @param preName
     * @param mode
     */
    public static void clearPreferences(String preName, int mode) {
        SharedPreferences preferences = ForMusicApplication.getInstance()
                .getSharedPreferences(preName, mode);
        Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * @param preName
     */
    public static void clearPreferences(String preName) {
        clearPreferences(preName, PREFERENCES_MODE);
    }

}
