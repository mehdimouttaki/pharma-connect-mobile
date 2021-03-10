package ma.pharmaconnect.pharmaconnect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import ma.pharmaconnect.pharmaconnect.dto.LoginDTO;

import static ma.pharmaconnect.pharmaconnect.Constant.BASE_URL;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (CurrentUserUtils.isConnected(this)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);
    }

    public void onClickButton(View view) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = BASE_URL + "/api/login";

        EditText usernameEditText = findViewById(R.id.usernameTxt);
        EditText passwordEditText = findViewById(R.id.passwordTxt);
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if ("".equals(username) || "".equals(password)) {
            //
        } else {
            LoginDTO loginDTO = new LoginDTO(username, password);
            String loginDTOJson = new Gson().toJson(loginDTO);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    new JSONObject(loginDTOJson), response -> {
                CurrentUserUtils.saveClientLocally(getApplicationContext(), response, password);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            },
                    error -> Log.e("HTTP_CALL", error.getMessage()));

            queue.add(jsonObjectRequest);
        }
    }


    public void onClickTextDontHaveAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }
}