package ma.pharmaconnect.pharmaconnect.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ma.pharmaconnect.pharmaconnect.CurrentUserUtils;
import ma.pharmaconnect.pharmaconnect.R;
import ma.pharmaconnect.pharmaconnect.dto.OrderShowDTO;
import ma.pharmaconnect.pharmaconnect.fragment.OrderFragment;

import static ma.pharmaconnect.pharmaconnect.Constant.BASE_URL;
import static ma.pharmaconnect.pharmaconnect.dto.OrderStatus.ATTACHED_TO_DELIVERY;
import static ma.pharmaconnect.pharmaconnect.dto.OrderStatus.DELIVERING;
import static ma.pharmaconnect.pharmaconnect.dto.OrderStatus.INIT;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private final Context context;
    private final OrderFragment orderFragment;
    // ... view holder defined above...

    // Store a member variable for the contacts
    private List<OrderShowDTO> orders = new ArrayList<>();

    // Pass in the contact array into the constructor
    public OrderAdapter(List<OrderShowDTO> ordersList, Context context, OrderFragment orderFragment) {
        this.orders = ordersList;
        this.context = context;
        this.orderFragment = orderFragment;
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView orderIdTextView, personNameTextView, statusTextView;
        public Button actionButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            orderIdTextView = itemView.findViewById(R.id.order_id);
            personNameTextView = itemView.findViewById(R.id.person_name);
            statusTextView = itemView.findViewById(R.id.order_status);
            actionButton = itemView.findViewById(R.id.action_btn);
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
        if (CurrentUserUtils.isClient(context)) {
            holder.personNameTextView.setText(
                    (order.getDeliveryMan() == null) ? "" : order.getDeliveryMan().getFullName()
            );
        } else {
            holder.personNameTextView.setText(order.getClient().getFullName());
        }
        holder.statusTextView.setText(order.getOrderStatus().name());
        if (INIT.equals(order.getOrderStatus()) && CurrentUserUtils.isDelivery(context)) {
            holder.actionButton.setVisibility(View.VISIBLE);
            holder.actionButton.setOnClickListener(v -> {
                changeOrderStatus(order.getId(), "take");
            });
        } else if (ATTACHED_TO_DELIVERY.equals(order.getOrderStatus()) && CurrentUserUtils.isDelivery(context)) {
            holder.actionButton.setVisibility(View.VISIBLE);
            holder.actionButton.setText("Ongoing");
            holder.actionButton.setOnClickListener(v -> {
                changeOrderStatus(order.getId(), "delivering");

            });
        } else if (DELIVERING.equals(order.getOrderStatus())) {
            holder.actionButton.setVisibility(View.VISIBLE);
            holder.actionButton.setText("Delivered");
            holder.actionButton.setOnClickListener(v -> {
                changeOrderStatus(order.getId(), "delivered");

            });
        }
    }


    @Override
    public int getItemCount() {
        return orders.size();
    }


    private void changeOrderStatus(Integer orderId, String status) {
        String url = BASE_URL + "/api/orders/" + status + "-it/" + orderId;


        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(url, response -> {
            orderFragment.initData();
            notifyDataSetChanged();
        }, error -> Log.e("HTTP_CALL", error.getMessage())) {
            @Override
            public Map<String, String> getHeaders() {
                return CurrentUserUtils.getMapHeaders(context);
            }
        };

        queue.add(request);
    }


}