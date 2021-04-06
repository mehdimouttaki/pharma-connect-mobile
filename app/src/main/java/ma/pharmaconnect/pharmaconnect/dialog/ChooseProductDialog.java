package ma.pharmaconnect.pharmaconnect.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ma.pharmaconnect.pharmaconnect.CurrentUserUtils;
import ma.pharmaconnect.pharmaconnect.R;
import ma.pharmaconnect.pharmaconnect.adapter.ProductSearchAdapter;
import ma.pharmaconnect.pharmaconnect.dto.ProductShowDTO;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static ma.pharmaconnect.pharmaconnect.Constant.BASE_URL;

public class ChooseProductDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity activity;
    public Button next;
    public EditText searchProductTxt;
    public TextView selectedItems;
    public RecyclerView rvProductsList;
    public List<ProductShowDTO> selectedProducts = new ArrayList();

    public ChooseProductDialog(Activity activity) {
        super(activity);
        this.activity = activity;

    }

    public void toggleItem(ProductShowDTO productShowDTO) {
        if (selectedProducts.contains(productShowDTO)) {
            selectedProducts.remove(productShowDTO);
        } else {
            selectedProducts.add(productShowDTO);
        }
        syncView();
    }

    private void syncView() {
        StringBuilder sb = new StringBuilder();
        for (ProductShowDTO p : selectedProducts) {
            sb.append(p.toString()).append("; ");
        }
        selectedItems.setText(sb.toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_create_order);
        searchProductTxt = findViewById(R.id.product_code_txt);
        selectedItems = findViewById(R.id.selected_items);
        searchProductTxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s != null && s.length() > 1) {
                    searchProducts(s);
                }
            }
        });
        rvProductsList = findViewById(R.id.product_search_rv);
        next = findViewById(R.id.next);
        next.setOnClickListener(this);
        getWindow().setLayout(1050, WRAP_CONTENT);
    }

    private void searchProducts(CharSequence query) {
        String url = BASE_URL + "/api/products?q=" + query;


        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(url, response -> {
            Type listType = new TypeToken<ArrayList<ProductShowDTO>>() {
            }.getType();
            List<ProductShowDTO> productShowDTOS = new Gson().fromJson(response, listType);
            changeRecyclerView(productShowDTOS);
        }, error -> Toast.makeText(getContext(), "Error " + error.getMessage(), Toast.LENGTH_LONG).show()) {
            @Override
            public Map<String, String> getHeaders() {
                return CurrentUserUtils.getMapHeaders(getContext());
            }
        };

        queue.add(request);
    }

    public void changeRecyclerView(List<ProductShowDTO> productShowDTOS) {
        // Initialize contacts
        // Create adapter passing in the sample user data
        ProductSearchAdapter adapter = new ProductSearchAdapter(productShowDTOS, getContext(), this);
        // Attach the adapter to the recyclerview to populate items
        rvProductsList.setAdapter(adapter);
        // Set layout manager to position the items
        rvProductsList.setLayoutManager(new LinearLayoutManager(getContext()));


        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                if (selectedProducts == null || selectedProducts.isEmpty()) {
                    Toast.makeText(activity.getApplicationContext(), "Enter a valid product", Toast.LENGTH_LONG).show();
                    return;
                }
                ChooseAddressDialog chooseAddressDialog = new ChooseAddressDialog(activity, selectedProducts);
                chooseAddressDialog.show();
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}