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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ma.pharmaconnect.pharmaconnect.CurrentUserUtils;
import ma.pharmaconnect.pharmaconnect.R;
import ma.pharmaconnect.pharmaconnect.adapter.PharmacyAdapter;
import ma.pharmaconnect.pharmaconnect.dto.PharmacyShowDTO;

import static ma.pharmaconnect.pharmaconnect.Constant.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PharamcyFragment extends Fragment {


    private RecyclerView pharmacyRV;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String url = BASE_URL + "/api/pharmacies";

        View view = inflater.inflate(R.layout.fragment_pharamcy, container, false);

        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        StringRequest request = new StringRequest(url, response -> {
            Type listType = new TypeToken<ArrayList<PharmacyShowDTO>>() {
            }.getType();
            List<PharmacyShowDTO> pharmacyShowDTOList = new Gson().fromJson(response, listType);
            changeRecyclerView(view.getContext(), pharmacyShowDTOList);
        }, error -> Toast.makeText(view.getContext(), "Error " + error.getMessage(), Toast.LENGTH_LONG).show()) {
            @Override
            public Map<String, String> getHeaders() {
                return CurrentUserUtils.getMapHeaders(view.getContext());
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