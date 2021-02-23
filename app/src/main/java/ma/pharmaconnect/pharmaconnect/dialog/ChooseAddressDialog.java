package ma.pharmaconnect.pharmaconnect.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import ma.pharmaconnect.pharmaconnect.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class ChooseAddressDialog extends Dialog implements View.OnClickListener {

    public Activity activity;
    public Button confirm;

    public ChooseAddressDialog(Activity activity) {
        super(activity);
        this.activity = activity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_choose_address);
        confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
        getWindow().setLayout(850, WRAP_CONTENT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                // call to the backend to save the order
                break;
            default:
                break;
        }
        dismiss();
    }
}