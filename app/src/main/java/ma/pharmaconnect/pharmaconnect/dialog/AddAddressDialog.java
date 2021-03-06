package ma.pharmaconnect.pharmaconnect.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import ma.pharmaconnect.pharmaconnect.CurrentUserUtils;
import ma.pharmaconnect.pharmaconnect.R;
import ma.pharmaconnect.pharmaconnect.dto.AddressCreationDTO;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static ma.pharmaconnect.pharmaconnect.Constant.BASE_URL;

public class AddAddressDialog extends Dialog implements View.OnClickListener {

    private final ChooseAddressDialog chooseAddressDialog;
    public final Activity activity;
    public Button add;
    public EditText numberTxt, streetTxt, detailTxt, zipTxt, cityTxt;


    public AddAddressDialog(Activity activity, ChooseAddressDialog chooseAddressDialog) {
        super(activity);
        this.activity = activity;
        this.chooseAddressDialog = chooseAddressDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_add_address);

        numberTxt = findViewById(R.id.address_number);
        streetTxt = findViewById(R.id.address_street);
        detailTxt = findViewById(R.id.address_detail);
        zipTxt = findViewById(R.id.address_zip);
        cityTxt = findViewById(R.id.address_city);


        add = findViewById(R.id.add);
        add.setOnClickListener(this);
        getWindow().setLayout(850, WRAP_CONTENT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                try {

                    AddressCreationDTO addressCreationDTO = new AddressCreationDTO(
                            numberTxt.getText().toString(),
                            streetTxt.getText().toString(),
                            detailTxt.getText().toString(),
                            zipTxt.getText().toString(),
                            cityTxt.getText().toString()
                    );
                    addAddress(addressCreationDTO);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        dismiss();
    }

    private void addAddress(AddressCreationDTO addressCreationDTO) throws JSONException {

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        String url = BASE_URL + "/api/address";


        String addressDTOJson = new Gson().toJson(addressCreationDTO);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(addressDTOJson),
                response -> {
                    Toast.makeText(activity.getApplicationContext(), "Address created successfully.", Toast.LENGTH_LONG).show();
                    chooseAddressDialog.show();
                },
                error -> Log.e("HTTP_CALL", error.toString())
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return CurrentUserUtils.getMapHeaders(activity.getApplicationContext());
            }
        };
        ;

        queue.add(jsonObjectRequest);

    }
}