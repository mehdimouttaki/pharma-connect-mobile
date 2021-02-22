package ma.pharmaconnect.pharmaconnect;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import org.json.JSONObject;

import ma.pharmaconnect.pharmaconnect.dto.ClientShowDTO;

public class CurrentUserUtils {

    public static String currentUsername(Context context) {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPref.getString("username", null);
    }

    public static String currentPassword(Context context) {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPref.getString("password", null);
    }

    public static boolean isConnected(Context context) {
        return currentUsername(context) != null;
    }

    public static boolean isNotConnected(Context context) {
        return currentUsername(context) == null;
    }


    public static void saveClientLocally(Context context, JSONObject response, String password) {
        ClientShowDTO clientShowDTO = new Gson().fromJson(response.toString(), ClientShowDTO.class);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", clientShowDTO.getUsername());
        editor.putString("firstName", clientShowDTO.getFirstName());
        editor.putString("lastName", clientShowDTO.getLastName());
        editor.putString("phone", clientShowDTO.getPhone());
        editor.putString("status", clientShowDTO.getStatus());
        editor.putString("role", clientShowDTO.getRole());
        editor.putString("password", password);
        editor.apply();
    }

    public static void clearUser(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }
}
