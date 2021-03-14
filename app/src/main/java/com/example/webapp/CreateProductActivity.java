package com.example.webapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;


public class CreateProductActivity extends BaseActivity implements View.OnClickListener {

    Button createNewProductButton;
    TextInputEditText productName;
    TextInputEditText productShortDescription;
    TextInputEditText productLongDescription;
    TextInputEditText productCategory;
    TextInputEditText productPrice;
    ByteArrayOutputStream productImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        initViews();
        createNewProductButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == createNewProductButton) {

           if(createNewProduct()) {
               goBackToMainScreen();
           }
        }
    }

    private void initViews() {
        createNewProductButton = findViewById(R.id.createProductAddButton);
        productName = findViewById(R.id.createProductName);
        productShortDescription = findViewById(R.id.createProductShortDescription);
        productLongDescription = findViewById(R.id.createProductLongDescription);
        productCategory = findViewById(R.id.createProductCategory);
        productPrice = findViewById(R.id.createProductPrice);
    }

    private boolean createNewProduct() {
        Product productToBeCreated = new Product();
        productToBeCreated.set_productName(String.valueOf(productName.getText()));
        productToBeCreated.set_shortDescription(String.valueOf(productShortDescription.getText()));
        productToBeCreated.set_mainDescription(String.valueOf(productLongDescription.getText()));
        productToBeCreated.set_dateAdded("15.15.15");
        productToBeCreated.set_category(String.valueOf(productCategory.getText()));
        productToBeCreated.set_productPrice(Double.parseDouble(String.valueOf(productPrice.getText())));
        productToBeCreated.set_productPicture(productImage);

        DatabaseOperations databaseOperations = new DatabaseOperations(this);
        return databaseOperations.createNewProduct(productToBeCreated);
    }

    private void goBackToMainScreen() {
        NavigationUtils navUtils = new NavigationUtils();
        boolean isThisAdmin = getIntent().getBooleanExtra("isThisAdmin", false);
        navUtils.moveToMainListActivity(this, isThisAdmin);
    }
}
