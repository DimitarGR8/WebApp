package com.example.webapp.activities.shoppingCart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.webapp.R;
import com.example.webapp.activities.baseActivity.BaseActivity;
import com.example.webapp.data.model.Product;
import com.example.webapp.utils.NavigationUtils;

import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.Objects;


public class ShoppingCartActivity extends BaseActivity implements View.OnClickListener, IShoppingCartActivity {

    RecyclerView cartRecyclerView;
    TextView cartSubtotalAmountLabel;
    TextView cartShippingAmountLabel;
    TextView cartTotalAmountLabel;
    Button cartCompleteOrderButton;
    ArrayList<Product> shoppingCartList;
    Double finalPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initViews();
        cartCompleteOrderButton.setOnClickListener(this);
        setupAdapter();
    }

    @Override
    public void onClick(View view) {
        if(view == cartCompleteOrderButton) {
            moveToCheckoutActivity();
        }
    }

    @Override
    public void onDeleteButtonClick(Product product) {
        shoppingCartList.remove(product);
        Objects.requireNonNull(cartRecyclerView.getAdapter()).notifyDataSetChanged();

        if(shoppingCartList.isEmpty()) {
            moveBackToMainListActivity();
        } else {
            setProperPrices();
        }
    }

    private void initViews() {
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartSubtotalAmountLabel = findViewById(R.id.cartSubtotalAmountLabel);
        cartShippingAmountLabel = findViewById(R.id.cartShippingAmountLabel);
        cartTotalAmountLabel = findViewById(R.id.cartTotalAmountLabel);
        cartCompleteOrderButton = findViewById(R.id.cartCompleteOrderButton);
    }

    private void setupAdapter() {
        Bundle bundle = getIntent().getExtras();
        shoppingCartList = bundle.getParcelableArrayList("shoppingCartList");

        setProperPrices();

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        cartRecyclerView.setAdapter(new ShoppingCartAdapter(shoppingCartList, ShoppingCartActivity.this));
    }

    private void setProperPrices() {
        ArrayList<String> pricesList = new ArrayList<>();
        shoppingCartList.forEach(product -> {
            pricesList.add(product.get_productPrice());
        });

        finalPrice = pricesList.stream().mapToDouble(Double::parseDouble).sum();
        double shippingPrice = Double.parseDouble(cartShippingAmountLabel.getText().toString());
        cartSubtotalAmountLabel.setText(String.valueOf(finalPrice));
        cartTotalAmountLabel.setText(String.valueOf(finalPrice + shippingPrice));
    }

    private void moveBackToMainListActivity() {
        NavigationUtils navigationUtils = new NavigationUtils();
        boolean isThisAdmin = getIntent().getBooleanExtra("isThisAdmin", false);
        navigationUtils.moveToMainListActivity(this, isThisAdmin);
    }

    private void moveToCheckoutActivity() {
        NavigationUtils navigationUtils = new NavigationUtils();
        boolean isThisAdmin = getIntent().getBooleanExtra("isThisAdmin", false);
        navigationUtils.moveToCheckoutActivity(this, isThisAdmin, finalPrice);
    }
}
