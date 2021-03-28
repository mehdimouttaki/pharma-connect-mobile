package ma.pharmaconnect.pharmaconnect.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import ma.pharmaconnect.pharmaconnect.CurrentUserUtils;
import ma.pharmaconnect.pharmaconnect.LoginActivity;
import ma.pharmaconnect.pharmaconnect.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Button logoutButton = view.findViewById(R.id.logout_btn);
        logoutButton.setOnClickListener(v -> logout());


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        String usernameFromSharedPref = sharedPref.getString("username", null);
        String firstNameFromSharedPref = sharedPref.getString("firstName", null);
        String lastNameFromSharedPref = sharedPref.getString("lastName", null);
        String phoneFromSharedPref = sharedPref.getString("phone", null);
        String roleFromSharedPref = sharedPref.getString("role", null);

        EditText usernameEditText = view.findViewById(R.id.usernameTxt);
        EditText firstNameEditText = view.findViewById(R.id.firstNameTxt);
        EditText lastNameEditText = view.findViewById(R.id.lastNameTxt);
        EditText phoneEditText = view.findViewById(R.id.phoneTxt);
        EditText passwordEditText = view.findViewById(R.id.passwordTxt);
        EditText roleEditText = view.findViewById(R.id.roleTxt);

        usernameEditText.setText(usernameFromSharedPref);
        firstNameEditText.setText(firstNameFromSharedPref);
        lastNameEditText.setText(lastNameFromSharedPref);
        phoneEditText.setText(phoneFromSharedPref);
        roleEditText.setText(roleFromSharedPref);
        passwordEditText.setText("******");

        return view;
    }


    public void logout() {
        CurrentUserUtils.clearUser(getActivity().getApplicationContext());
        startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
        getActivity().finish();
    }

}