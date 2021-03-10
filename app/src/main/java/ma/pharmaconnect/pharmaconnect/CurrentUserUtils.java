package ma.pharmaconnect.pharmaconnect;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    public static Map<String, String> getMapHeaders(Context ctx) {
        Map<String, String> params = new HashMap<>();
        params.put("username", CurrentUserUtils.currentUsername(ctx));
        params.put("password", CurrentUserUtils.currentPassword(ctx));
        return params;
    }

    public static String getRole(Context ctx) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sharedPref.getString("role", null);
    }

    public static boolean isDelivery(Context ctx) {
        return "DELIVERY".equalsIgnoreCase(getRole(ctx));
    }

    public static boolean isClient(Context ctx) {
        return "CLIENT".equalsIgnoreCase(getRole(ctx));
    }
}
