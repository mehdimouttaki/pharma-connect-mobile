package ma.pharmaconnect.pharmaconnect.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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
import ma.pharmaconnect.pharmaconnect.dto.OrderShowDTO;
import ma.pharmaconnect.pharmaconnect.dto.ReviewCreationDTO;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static ma.pharmaconnect.pharmaconnect.Constant.BASE_URL;

public class ReviewDialog extends Dialog implements View.OnClickListener {

    private final OrderShowDTO order;
    public final Activity activity;

    public Button reviewBtn;
    public EditText reviewTxt;
    public RatingBar ratingBarReview;


    public ReviewDialog(Activity activity, OrderShowDTO orderShowDTO) {
        super(activity);
        this.activity = activity;
        this.order = orderShowDTO;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_review);

        reviewTxt = findViewById(R.id.reviewTxt);
        ratingBarReview = findViewById(R.id.ratingBarReview);


        reviewBtn = findViewById(R.id.reviewBtn);
        reviewBtn.setOnClickListener(this);
        getWindow().setLayout(1050, WRAP_CONTENT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reviewBtn:
                try {
                    sendReview();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        dismiss();
    }

    private void sendReview() throws JSONException {

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        String url = BASE_URL + "/api/reviews";

        ReviewCreationDTO reviewCreationDTO = new ReviewCreationDTO();
        reviewCreationDTO.setRate((int) ratingBarReview.getRating());
        reviewCreationDTO.setLabel(reviewTxt.getText().toString());
        reviewCreationDTO.setOrderId(order.getId());
        reviewCreationDTO.setDeliveryManId(order.getDeliveryMan().getId());

        String addressDTOJson = new Gson().toJson(reviewCreationDTO);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(addressDTOJson),
                response -> {
                    Toast.makeText(activity.getApplicationContext(), "Thanks for reviewing our services.", Toast.LENGTH_LONG).show();
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