package ma.pharmaconnect.pharmaconnect.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ma.pharmaconnect.pharmaconnect.CurrentUserUtils;
import ma.pharmaconnect.pharmaconnect.R;
import ma.pharmaconnect.pharmaconnect.adapter.OrderAdapter;
import ma.pharmaconnect.pharmaconnect.dialog.ChooseProductDialog;
import ma.pharmaconnect.pharmaconnect.dto.OrderShowDTO;

import static ma.pharmaconnect.pharmaconnect.Constant.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {


    private RecyclerView orderRV;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        FloatingActionButton fab = view.findViewById(R.id.create_order);
        fab.setOnClickListener(v -> showEnterProductCodePopUp());
        if (CurrentUserUtils.isDelivery(view.getContext())) {
            fab.setVisibility(View.GONE);
        }


        initData();

        // Lookup the recyclerview in activity layout
        orderRV = view.findViewById(R.id.order_recycler_view);


        return view;
    }

    public void initData() {
        String url = BASE_URL + "/api/orders";


        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(url, response -> {
            Type listType = new TypeToken<ArrayList<OrderShowDTO>>() {
            }.getType();
            List<OrderShowDTO> orderDTOS = new Gson().fromJson(response, listType);
            changeRecyclerView(getContext(), orderDTOS);
        }, error -> Toast.makeText(getContext(), "Error " + error.getMessage(), Toast.LENGTH_LONG).show()) {
            @Override
            public Map<String, String> getHeaders() {
                return CurrentUserUtils.getMapHeaders(getContext());
            }
        };

        queue.add(request);
    }

    private void showEnterProductCodePopUp() {
        ChooseProductDialog chooseProductDialog = new ChooseProductDialog(getActivity());
        chooseProductDialog.show();
    }


    public void changeRecyclerView(Context context, List<OrderShowDTO> orders) {
        // Initialize contacts
        // Create adapter passing in the sample user data
        OrderAdapter adapter = new OrderAdapter(orders, getContext() , this);
        // Attach the adapter to the recyclerview to populate items
        orderRV.setAdapter(adapter);
        // Set layout manager to position the items
        orderRV.setLayoutManager(new LinearLayoutManager(context));

        adapter.notifyDataSetChanged();

    }
}