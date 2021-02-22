package ma.pharmaconnect.pharmaconnect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ma.pharmaconnect.pharmaconnect.R;
import ma.pharmaconnect.pharmaconnect.dto.PharmacyShowDTO;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.ViewHolder> {
    // ... view holder defined above...

    // Store a member variable for the contacts
    private List<PharmacyShowDTO> pharmacies = new ArrayList<>();

    // Pass in the contact array into the constructor
    public PharmacyAdapter(List<PharmacyShowDTO> pharmacyShowDTOS) {
        this.pharmacies = pharmacyShowDTOS;
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView labelTextView;
        public TextView cityTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            labelTextView = (TextView) itemView.findViewById(R.id.pharmacy_label);
            cityTextView = (TextView) itemView.findViewById(R.id.pharmacy_city);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_pharmacy, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        PharmacyShowDTO pharmacy = pharmacies.get(position);

        // Set item views based on your views and data model
        holder.labelTextView.setText(pharmacy.getName());
        holder.cityTextView.setText(pharmacy.getCity().getLabel());
    }

    @Override
    public int getItemCount() {
        return pharmacies.size();
    }

}