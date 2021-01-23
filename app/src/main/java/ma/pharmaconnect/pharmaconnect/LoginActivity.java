package ma.pharmaconnect.pharmaconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickButton(View view) {
        Intent intent =new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickTextDontHaveAccount(View view) {
        Intent intent =new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }
}