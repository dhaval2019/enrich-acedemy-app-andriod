package enrich.enrichacademy.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by Admin on 02-Mar-17.
 */

public class SharedPreferenceStore {

    /**
     * Delete the values for the KEY
     *
     * @param context Context for SharedPreference
     * @param key     KEY to delete value
     */
    public static void deleteValue(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key).commit();
    }

    /**
     * Store value for Key
     *
     * @param context Context for SharedPreference
     * @param key     KEY to store value
     * @param value   String Value to be stored
     */
    public static void storeValue(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value).commit();
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(key));
    }

    /**
     * Store value for Key
     *
     * @param context Context for SharedPreference
     * @param key     KEY to store value
     * @param value   Boolean Value to be stored
     */
    public static void storeValue(Context context, String key, boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value).apply();
    }

    /**
     * Store value for Key
     *
     * @param context Context for SharedPreference
     * @param key     KEY to store value
     * @param value   Double Value to be stored
     */
    public static void storeValue(Context context, String key, double value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String doubleVal = String.valueOf(value);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, doubleVal).apply();
    }

    /**
     * Store value for Key
     *
     * @param context Context for SharedPreference
     * @param key     KEY to store value
     * @param value   Flot Value to be stored
     */
    public static void storeValue(Context context, String key, float value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value).apply();
    }

    /**
     * Store value for Key
     *
     * @param context Context for SharedPreference
     * @param key     KEY to store value
     * @param value   Long Value to be stored
     */
    public static void storeValue(Context context, String key, long value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value).apply();
    }

    /**
     * Store value for Key
     *
     * @param context Context for SharedPreference
     * @param key     KEY to store value
     * @param value   Integer Value to be stored
     */
    public static void storeValue(Context context, String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value).apply();
    }

    /**
     * Store value for Key
     *
     * @param context Context for SharedPreference
     * @param key     KEY to store value
     * @param value   Bitmap Value to be stored
     */
    public static void storeValue(Context context, String key, Bitmap value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, EnrichUtils.encodeTobase64(value)).apply();
    }

    /**
     * Get Value for Key
     *
     * @param context  Context for SharedPreference
     * @param key      KEY to get value
     * @param defValue Default String Value
     * @return Returns String
     */

    public static String getValue(Context context, String key, String defValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, defValue);
    }

    /**
     * Get Value for Key
     *
     * @param context  Context for SharedPreference
     * @param key      KEY to get value
     * @param defValue Default Boolean Value
     * @return Returns Boolean
     */
    public static boolean getValue(Context context, String key, boolean defValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, defValue);
    }

    /**
     * Get Value for Key
     *
     * @param context  Context for SharedPreference
     * @param key      KEY to get value
     * @param defValue Default Double Value
     * @return Returns Double
     */
    public static double getValue(Context context, String key, double defValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String doubleVal = String.valueOf(defValue);
        return Double.parseDouble(preferences.getString(key, doubleVal));
    }

    /**
     * Get Value for Key
     *
     * @param context  Context for SharedPreference
     * @param key      KEY to get value
     * @param defValue Default Float Value
     * @return Returns Float
     */
    public static float getValue(Context context, String key, float defValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getFloat(key, defValue);
    }

    /**
     * Get Value for Key
     *
     * @param context  Context for SharedPreference
     * @param key      KEY to get value
     * @param defValue Default Long Value
     * @return Returns Long
     */
    public static long getValue(Context context, String key, long defValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(key, defValue);
    }

    /**
     * Get Value for Key
     *
     * @param context  Context for SharedPreference
     * @param key      KEY to get value
     * @param defValue Default Integer Value
     * @return Returns Integer
     */
    public static int getValue(Context context, String key, int defValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, defValue);
    }

    /**
     * Get Value for Key
     *
     * @param context  Context for SharedPreference
     * @param key      KEY to get value
     * @param defValue Default Bitmap Value
     * @return Returns Bitmap
     */
    public static Bitmap getValue(Context context, String key, Bitmap defValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String imageData = preferences.getString(key, null);
        if (imageData != null && !imageData.equals(""))
            return EnrichUtils.decodeBase64(imageData);
        return null;
    }
}
