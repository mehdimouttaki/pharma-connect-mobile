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
import ma.pharmaconnect.pharmaconnect.dialog.ChooseProductDialog;
import ma.pharmaconnect.pharmaconnect.dto.ProductShowDTO;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ProductSearchAdapter extends RecyclerView.Adapter<ProductSearchAdapter.ViewHolder> {
    private final Context context;
    private final ChooseProductDialog chooseProductDialog;

    // ... view holder defined above...

    // Store a member variable for the contacts
    private List<ProductShowDTO> products = new ArrayList<>();

    // Pass in the contact array into the constructor
    public ProductSearchAdapter(List<ProductShowDTO> productShowDTOList, Context context, ChooseProductDialog chooseProductDialog) {
        this.products = productShowDTOList;
        this.context = context;
        this.chooseProductDialog = chooseProductDialog;
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView productLabel, productState;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            productLabel = itemView.findViewById(R.id.product_label);
            productState = itemView.findViewById(R.id.state);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_product, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        ProductShowDTO productShowDTO = products.get(position);

        // Set item views based on your views and data model
        holder.productLabel.setText(productShowDTO.toString());

        holder.itemView.setOnClickListener(v -> {
            productShowDTO.setSelected(!productShowDTO.isSelected());
            chooseProductDialog.toggleItem(productShowDTO);
            if (productShowDTO.isSelected()) {
                holder.productState.setText("X");
            } else {
                holder.productState.setText("");
            }
        });

    }


    @Override
    public int getItemCount() {
        return products.size();
    }


}