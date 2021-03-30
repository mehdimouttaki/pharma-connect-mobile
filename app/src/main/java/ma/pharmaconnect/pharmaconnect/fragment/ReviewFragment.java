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
import ma.pharmaconnect.pharmaconnect.adapter.ReviewAdapter;
import ma.pharmaconnect.pharmaconnect.dto.ReviewShowDTO;

import static ma.pharmaconnect.pharmaconnect.Constant.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ReviewFragment extends Fragment {
    private RecyclerView reviewRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        reviewRecyclerView = view.findViewById(R.id.review_recycler_view);

        initData();

        return view;
    }


    public void initData() {
        String url = BASE_URL + "/api/reviews";


        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(url, response -> {
            Type listType = new TypeToken<ArrayList<ReviewShowDTO>>() {
            }.getType();
            List<ReviewShowDTO> orderDTOS = new Gson().fromJson(response, listType);
            changeRecyclerView(getContext(), orderDTOS);
        }, error -> Toast.makeText(getContext(), "Error " + error.getMessage(), Toast.LENGTH_LONG).show()) {
            @Override
            public Map<String, String> getHeaders() {
                return CurrentUserUtils.getMapHeaders(getContext());
            }
        };

        queue.add(request);
    }

    public void changeRecyclerView(Context context, List<ReviewShowDTO> orders) {
        // Initialize contacts
        // Create adapter passing in the sample user data
        ReviewAdapter adapter = new ReviewAdapter(orders, getContext());
        // Attach the adapter to the recyclerview to populate items
        reviewRecyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        adapter.notifyDataSetChanged();

    }
}