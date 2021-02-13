package ma.pharmaconnect.pharmaconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String usernameFromSharedPref = sharedPref.getString("username", null);


        String firstNameFromSharedPref = sharedPref.getString("firstName", null);
        String lastNameFromSharedPref = sharedPref.getString("lastName", null);
        String phoneFromSharedPref = sharedPref.getString("phone", null);

        EditText usernameEditText = findViewById(R.id.usernameTxt);
        EditText firstNameEditText = findViewById(R.id.firstNameTxt);
        EditText lastNameEditText = findViewById(R.id.lastNameTxt);
        EditText phoneEditText = findViewById(R.id.phoneTxt);
        EditText passwordEditText = findViewById(R.id.passwordTxt);

        usernameEditText.setText(usernameFromSharedPref);
        firstNameEditText.setText(firstNameFromSharedPref);
        lastNameEditText.setText(lastNameFromSharedPref);
        phoneEditText.setText(phoneFromSharedPref);
        passwordEditText.setText("******");
    }

    public void onClickLogout(View view) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        finish();
    }
}