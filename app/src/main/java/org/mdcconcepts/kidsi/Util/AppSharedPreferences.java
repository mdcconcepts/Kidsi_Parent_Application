package org.mdcconcepts.kidsi.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * This class contains all data saved in shared preferences
 * 
 * @author Admin
 * 
 */
public class AppSharedPreferences {

	/**
	 * Get Preferance object 
	 */
	private static SharedPreferences getMyPreferences(Context context) {
		return context.getSharedPreferences(Util.APP_PREFERENCES,
				Context.MODE_PRIVATE);
	}

	/**
	 * Set Uid/Retailer_Id
	 * 
	 * @param context
	 * @param value
	 */
	public static void setUname(Context context, String value) {
		final SharedPreferences prefs = getMyPreferences(context);
		Editor editor = prefs.edit();
		editor.putString("Uname", value);
		editor.commit();
	}

	/**
	 * Get Uid/Retailer_Id
	 * 
	 * @param context
	 * @return
	 */
	public static String getUname(Context context) {
		final SharedPreferences prefs = getMyPreferences(context);
		String value = prefs.getString("Uname", "");
		return value;
	}


    /**
     * Set Uid/Retailer_Id
     *
     * @param context
     * @param value
     */
    public static void setPassword(Context context, String value) {
        final SharedPreferences prefs = getMyPreferences(context);
        Editor editor = prefs.edit();
        editor.putString("Password", value);
        editor.commit();
    }

    /**
     * Get Uid/Retailer_Id
     *
     * @param context
     * @return
     */
    public static String getPassword(Context context) {
        final SharedPreferences prefs = getMyPreferences(context);
        String value = prefs.getString("Password", "");
        return value;
    }

}
