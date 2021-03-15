package ma.pharmaconnect.pharmaconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ma.pharmaconnect.pharmaconnect.fragment.ChatFragment;
import ma.pharmaconnect.pharmaconnect.fragment.OrderFragment;
import ma.pharmaconnect.pharmaconnect.fragment.PharamcyFragment;
import ma.pharmaconnect.pharmaconnect.fragment.ProfileFragment;
import ma.pharmaconnect.pharmaconnect.fragment.ReviewFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (CurrentUserUtils.isNotConnected(this)) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // as soon as the application opens the first
        // fragment should be shown to the user
        // in this case it is algorithm fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OrderFragment()).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // By using switch we can easily get
            // the selected fragment
            // by using there id.
            Fragment selectedFragment = new OrderFragment();
            switch (item.getItemId()) {
                case R.id.pharmacy_item:
                    selectedFragment = new PharamcyFragment();
                    break;
                case R.id.orders_item:
                    selectedFragment = new OrderFragment();
                    break;
                case R.id.profile_item:
                    selectedFragment = new ProfileFragment();
                    break;
                case R.id.chat:
                    selectedFragment = new ChatFragment();
                    break;
                case R.id.review:
                    selectedFragment = new ReviewFragment();
                    break;
            }
            // It will help to replace the
            // one fragment to other.
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
            return true;
        }
    };
}