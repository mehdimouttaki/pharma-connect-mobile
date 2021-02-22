package ma.pharmaconnect.pharmaconnect.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ma.pharmaconnect.pharmaconnect.CurrentUserUtils;
import ma.pharmaconnect.pharmaconnect.R;
import ma.pharmaconnect.pharmaconnect.adapter.PharmacyAdapter;
import ma.pharmaconnect.pharmaconnect.dto.PharmacyShowDTO;

import static ma.pharmaconnect.pharmaconnect.Constant.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PharamcyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PharamcyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView pharmacyRV;

    public PharamcyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PharamcyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PharamcyFragment newInstance(String param1, String param2) {
        PharamcyFragment fragment = new PharamcyFragment();
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
        String url = BASE_URL + "/api/pharmacies";

        View view = inflater.inflate(R.layout.fragment_pharamcy, container, false);

        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type listType = new TypeToken<ArrayList<PharmacyShowDTO>>() {
                }.getType();
                List<PharmacyShowDTO> pharmacyShowDTOList = new Gson().fromJson(response, listType);
                changeRecyclerView(view.getContext(), pharmacyShowDTOList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<>();
                params.put("username", CurrentUserUtils.currentUsername(view.getContext()));
                params.put("password", CurrentUserUtils.currentPassword(view.getContext()));
                return params;
            }
        };

        queue.add(request);

        // Lookup the recyclerview in activity layout
        pharmacyRV = (RecyclerView) view.findViewById(R.id.pharmacy_recycler_view);


        return view;
    }


    public void changeRecyclerView(Context context, List<PharmacyShowDTO> pharmacies) {
        // Initialize contacts
        // Create adapter passing in the sample user data
        PharmacyAdapter adapter = new PharmacyAdapter(pharmacies);
        // Attach the adapter to the recyclerview to populate items
        pharmacyRV.setAdapter(adapter);
        // Set layout manager to position the items
        pharmacyRV.setLayoutManager(new LinearLayoutManager(context));


        adapter.notifyDataSetChanged();

    }
}