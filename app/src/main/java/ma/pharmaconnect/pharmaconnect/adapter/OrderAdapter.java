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
import ma.pharmaconnect.pharmaconnect.dto.OrderShowDTO;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    // ... view holder defined above...

    // Store a member variable for the contacts
    private List<OrderShowDTO> orders = new ArrayList<>();

    // Pass in the contact array into the constructor
    public OrderAdapter(List<OrderShowDTO> ordersList) {
        this.orders = ordersList;
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView orderIdTextView;
        public TextView personNameTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            orderIdTextView = itemView.findViewById(R.id.order_id);
            personNameTextView = itemView.findViewById(R.id.person_name);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_order, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        OrderShowDTO order = orders.get(position);

        // Set item views based on your views and data model
        holder.orderIdTextView.setText(String.valueOf(order.getId()));
        holder.personNameTextView.setText(order.getClient().getFirstName());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

}