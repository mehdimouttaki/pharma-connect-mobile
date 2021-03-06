package ma.pharmaconnect.pharmaconnect.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ma.pharmaconnect.pharmaconnect.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class ChooseProductDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity activity;
    public Button next;
    public EditText codeProductTxt;

    public ChooseProductDialog(Activity activity) {
        super(activity);
        this.activity = activity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_create_order);
        codeProductTxt = findViewById(R.id.product_code_txt);
        next = findViewById(R.id.next);
        next.setOnClickListener(this);
        getWindow().setLayout(850, WRAP_CONTENT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                String codeProduct = codeProductTxt.getText().toString();
                if (codeProduct == null || codeProduct.trim().isEmpty()) {
                    Toast.makeText(activity.getApplicationContext(), "Enter a valid product", Toast.LENGTH_LONG).show();
                    return;
                }
                ChooseAddressDialog chooseAddressDialog = new ChooseAddressDialog(activity, codeProduct);
                chooseAddressDialog.show();
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}