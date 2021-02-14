package ma.pharmaconnect.pharmaconnect;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        EditText usernameEditText = view.findViewById(R.id.usernameTxt);
        EditText firstNameEditText = view.findViewById(R.id.firstNameTxt);
        EditText lastNameEditText = view.findViewById(R.id.lastNameTxt);
        EditText phoneEditText = view.findViewById(R.id.phoneTxt);
        EditText passwordEditText = view.findViewById(R.id.passwordTxt);

        usernameEditText.setText(usernameFromSharedPref);
        firstNameEditText.setText(firstNameFromSharedPref);
        lastNameEditText.setText(lastNameFromSharedPref);
        phoneEditText.setText(phoneFromSharedPref);
        passwordEditText.setText("******");

        return view;
    }


    public void logout() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
        getActivity().finish();
    }

}