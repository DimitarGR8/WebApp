package com.example.webapp.activities.checkoutActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.webapp.R;
import com.example.webapp.activities.baseActivity.BaseActivity;
import com.google.android.material.textfield.TextInputEditText;
import org.jetbrains.annotations.Nullable;


public class CheckoutActivity extends BaseActivity implements View.OnClickListener {

    Button checkoutBackButton;
    TextInputEditText checkoutInputFieldFirstName;
    TextInputEditText checkoutInputFieldLastName;
    TextInputEditText checkoutInputFieldEmail;
    TextInputEditText checkoutInputFieldPhone;
    TextInputEditText checkoutInputFieldCountry;
    TextInputEditText checkoutInputFieldTown;
    TextInputEditText checkoutInputFieldAddress;
    TextInputEditText checkoutInputFieldPostalCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        initViews();

        checkoutBackButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == checkoutBackButton) {
            super.onBackPressed();
        }
    }

    private void initViews() {
        checkoutInputFieldFirstName = findViewById(R.id.checkoutInputFieldFirstName);
        checkoutInputFieldLastName = findViewById(R.id.checkoutInputFieldLastName);
        checkoutInputFieldEmail = findViewById(R.id.checkoutInputFieldEmail);
        checkoutInputFieldPhone = findViewById(R.id.checkoutInputFieldPhone);
        checkoutInputFieldCountry = findViewById(R.id.checkoutInputFieldCountry);
        checkoutInputFieldTown = findViewById(R.id.checkoutInputFieldTown);
        checkoutInputFieldAddress = findViewById(R.id.checkoutInputFieldAddress);
        checkoutInputFieldPostalCode = findViewById(R.id.checkoutInputFieldPostalCode);

        checkoutBackButton = findViewById(R.id.checkoutBackButton);
    }
}
