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
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView orderRV;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
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