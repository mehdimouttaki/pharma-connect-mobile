package ma.pharmaconnect.pharmaconnect.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ma.pharmaconnect.pharmaconnect.CurrentUserUtils;
import ma.pharmaconnect.pharmaconnect.R;
import ma.pharmaconnect.pharmaconnect.dto.OrderCreationDTO;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static ma.pharmaconnect.pharmaconnect.Constant.BASE_URL;

public class ChooseAddressDialog extends Dialog implements View.OnClickListener {

    public Activity activity;
    public Button confirm;

    public ChooseAddressDialog(Activity activity) {
        super(activity);
        this.activity = activity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_choose_address);
        confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
        getWindow().setLayout(850, WRAP_CONTENT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                try {
                    createOrder("DL6565", "25 rue de laymoune, sidi maarouf");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        dismiss();
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
                error -> Toast.makeText(activity.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show()
        ){
            @Override
            public Map<String, String> getHeaders() {
                return CurrentUserUtils.getMapHeaders(activity.getApplicationContext());
            }
        };;

        queue.add(jsonObjectRequest);

    }
}