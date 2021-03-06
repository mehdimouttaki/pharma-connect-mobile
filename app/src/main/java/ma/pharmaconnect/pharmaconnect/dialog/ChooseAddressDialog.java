package ma.pharmaconnect.pharmaconnect.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ma.pharmaconnect.pharmaconnect.CurrentUserUtils;
import ma.pharmaconnect.pharmaconnect.R;
import ma.pharmaconnect.pharmaconnect.dto.OrderCreationDTO;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static ma.pharmaconnect.pharmaconnect.Constant.BASE_URL;

public class ChooseAddressDialog extends Dialog implements View.OnClickListener {

    public Activity activity;
    public Button confirm, addOtherField;
    private RadioGroup groupAddress;
    private final String codeProduct;
    private List<String> addresses = new ArrayList<>();

    public ChooseAddressDialog(Activity activity, String codeProduct) {
        super(activity);
        this.activity = activity;
        this.codeProduct = codeProduct;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_choose_address);
        confirm = findViewById(R.id.confirm);
        addOtherField = findViewById(R.id.add_another_field);
        groupAddress = findViewById(R.id.group_address);
        addOtherField.setOnClickListener(this);
        confirm.setOnClickListener(this);
        getAllAddresses();
        getWindow().setLayout(850, WRAP_CONTENT);
    }

    private void getAllAddresses() {
        String url = BASE_URL + "/api/my-address";

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());

        StringRequest request = new StringRequest(url, response -> {
            Type listType = new TypeToken<ArrayList<String>>() {
            }.getType();
            addresses = new Gson().fromJson(response, listType);
            refreshGroupAddress();
        }, error -> Toast.makeText(activity.getApplicationContext(), "Error " + error.getMessage(), Toast.LENGTH_LONG).show()) {
            @Override
            public Map<String, String> getHeaders() {
                return CurrentUserUtils.getMapHeaders(activity.getApplicationContext());
            }
        };

        queue.add(request);
    }

    private void refreshGroupAddress() {
        groupAddress.clearCheck();
        groupAddress.removeAllViews();
        if (addresses.isEmpty()) {
            return;
        }
        final RadioButton[] rb = new RadioButton[addresses.size()];
        for (int i = 0; i < rb.length; i++) {
            rb[i] = new RadioButton(activity.getApplicationContext());
            rb[i].setText(addresses.get(i));
            rb[i].setId(i + 100);
            groupAddress.addView(rb[i]);
        }
    }

    @Override
    public void show() {
        super.show();
        getAllAddresses();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                try {
                    int selectedId = groupAddress.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    RadioButton radioAddress = findViewById(selectedId);
                    createOrder(codeProduct, radioAddress.getText().toString());
                    dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.add_another_field:
                AddAddressDialog addAddressDialog = new AddAddressDialog(activity, this);
                addAddressDialog.show();
                hide();
                break;
            default:
                break;
        }

    }

    private void createOrder(String productCode, String address) throws JSONException {

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        String url = BASE_URL + "/api/orders";
        OrderCreationDTO orderCreationDTO = new OrderCreationDTO(productCode, address);


        String orderDTOJson = new Gson().toJson(orderCreationDTO);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(orderDTOJson),
                response -> Toast.makeText(activity.getApplicationContext(), "Order created successfully.", Toast.LENGTH_LONG).show(),
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