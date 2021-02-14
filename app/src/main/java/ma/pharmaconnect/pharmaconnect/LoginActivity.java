package ma.pharmaconnect.pharmaconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import ma.pharmaconnect.pharmaconnect.dto.ClientShowDTO;
import ma.pharmaconnect.pharmaconnect.dto.LoginDTO;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String usernameFromSharedPref = sharedPref.getString("username", null);
        if (usernameFromSharedPref != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);
    }

    public void onClickButton(View view) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.128.72.17:8080/api/login";

        EditText usernameEditText = findViewById(R.id.usernameTxt);
        EditText passwordEditText = findViewById(R.id.passwordTxt);
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if ("".equals(username) || "".equals(password)) {
            //
        } else {
            LoginDTO loginDTO = new LoginDTO(username, password);
            String loginDTOJson = new Gson().toJson(loginDTO);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(loginDTOJson), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    saveClientLocally(response);
                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            queue.add(jsonObjectRequest);
        }
    }

    private void saveClientLocally(JSONObject response) {
        ClientShowDTO clientShowDTO = new Gson().fromJson(response.toString(), ClientShowDTO.class);
        Toast.makeText(this, clientShowDTO.toString(), Toast.LENGTH_LONG).show();
        //hna anssjloh
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", clientShowDTO.getUsername());
        editor.putString("firstName", clientShowDTO.getFirstName());
        editor.putString("lastName", clientShowDTO.getLastName());
        editor.putString("phone", clientShowDTO.getPhone());
        editor.putString("status", clientShowDTO.getStatus());
        editor.putString("role", clientShowDTO.getRole());
        editor.apply();
    }

    public void onClickTextDontHaveAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }
}