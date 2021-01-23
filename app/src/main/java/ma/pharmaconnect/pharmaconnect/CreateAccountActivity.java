package ma.pharmaconnect.pharmaconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void onClickButton(View view) {
        Intent intent =new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClickTextHaveAlreadyAccount(View view) {
        Intent intent =new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}