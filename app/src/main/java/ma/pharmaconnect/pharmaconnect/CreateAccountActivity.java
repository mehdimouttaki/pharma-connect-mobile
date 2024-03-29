package ma.pharmaconnect.pharmaconnect;

import android.content.Intent;
import android.os.Bundle;
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

import ma.pharmaconnect.pharmaconnect.dto.ClientCreationDTO;

import static ma.pharmaconnect.pharmaconnect.Constant.BASE_URL;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void onClickButton(View view) {

        EditText firstNameEditText = findViewById(R.id.firstNameTxt);
        EditText lastNameEditText = findViewById(R.id.lastNameTxt);
        EditText phoneNameEditText = findViewById(R.id.phoneTxt);
        EditText emailNameEditText = findViewById(R.id.usernameTxt);
        EditText passwordNameEditText = findViewById(R.id.passwordTxt);

        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String phone = phoneNameEditText.getText().toString();
        String email = emailNameEditText.getText().toString();
        String password = passwordNameEditText.getText().toString();

        try {
            sendData(firstName, lastName, phone, email, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void onClickTextHaveAlreadyAccount(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    private void sendData(String firstName, String lastName, String phone, String email, String password) throws JSONException {

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = BASE_URL + "/api/clients";

        ClientCreationDTO clientCreationDTO = new ClientCreationDTO(firstName, lastName, email, phone, password);

        String clientCreationDTOJson = new Gson().toJson(clientCreationDTO);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(clientCreationDTOJson), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                CurrentUserUtils.saveClientLocally(getApplicationContext(), response, password);
                startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CreateAccountActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(jsonObjectRequest);

    }

}