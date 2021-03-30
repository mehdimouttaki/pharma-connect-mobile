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

import ma.pharmaconnect.pharmaconnect.CurrentUserUtils;
import ma.pharmaconnect.pharmaconnect.R;
import ma.pharmaconnect.pharmaconnect.dto.ReviewShowDTO;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private final Context context;
    // ... view holder defined above...

    // Store a member variable for the contacts
    private List<ReviewShowDTO> reviews = new ArrayList<>();

    // Pass in the contact array into the constructor
    public ReviewAdapter(List<ReviewShowDTO> ordersList, Context context) {
        this.reviews = ordersList;
        this.context = context;
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView reviewLabel, personNameTextView, reviewRate;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            reviewLabel = itemView.findViewById(R.id.review_label);
            reviewRate = itemView.findViewById(R.id.rate_review);
            personNameTextView = itemView.findViewById(R.id.person_name);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_review, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        ReviewShowDTO reviewShowDTO = reviews.get(position);

        // Set item views based on your views and data model
        holder.reviewLabel.setText(reviewShowDTO.getLabel());
        if (CurrentUserUtils.isClient(context)) {
            holder.personNameTextView.setText(
                    (reviewShowDTO.getDeliveryMan() == null) ? "" : reviewShowDTO.getDeliveryMan().getFullName()
            );
        } else {
            holder.personNameTextView.setText(reviewShowDTO.getClient().getFullName());
        }
        holder.reviewRate.setText(" "+reviewShowDTO.getRate() + " / 5");

    }


    @Override
    public int getItemCount() {
        return reviews.size();
    }


}