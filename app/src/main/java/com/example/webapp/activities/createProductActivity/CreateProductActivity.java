package com.example.webapp.activities.createProductActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.webapp.utils.BitmapConverter;
import com.example.webapp.data.database.DatabaseOperations;
import com.example.webapp.activities.ImageMainListViewModel;
import com.example.webapp.R;
import com.example.webapp.activities.baseActivity.BaseActivity;
import com.example.webapp.data.model.Product;
import com.example.webapp.utils.NavigationUtils;
import com.google.android.material.textfield.TextInputEditText;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CreateProductActivity extends BaseActivity implements View.OnClickListener {

    Button createNewProductButton;
    ImageButton createProductAddImageButton;
    TextInputEditText productName;
    TextInputEditText productShortDescription;
    TextInputEditText productLongDescription;
    TextInputEditText productCategory;
    TextInputEditText productPrice;
    ImageView productImage;
    ImageMainListViewModel viewModel;
    MutableLiveData<Bitmap> bitmapToSet = new MutableLiveData<>();

    String[] permissions = {Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        viewModel = new ViewModelProvider(this).get(ImageMainListViewModel.class);
        initViews();


        createNewProductButton.setOnClickListener(this);
        createProductAddImageButton.setOnClickListener(this);

        viewModel.getBitmap().observe(this, bitmap -> {
            bitmapToSet.postValue(bitmap);
            productImage.setImageBitmap(bitmap);
            }
        );
    }

    @Override
    public void onClick(View view) {
        if (view == createNewProductButton) {
           if(createNewProduct()) {
               goBackToMainScreen();
           }
        } else if(view == createProductAddImageButton) {
            if(allPermissionsGranted()) {
                startCamera();
            } else {
                ActivityCompat.requestPermissions(this, permissions, RC_PERMISSION);
            }
        }
    }

    private void initViews() {
        createNewProductButton = findViewById(R.id.createProductAddButton);
        createProductAddImageButton = findViewById(R.id.createProductAddImageButton);
        productImage = findViewById(R.id.createProductImage);
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
        productToBeCreated.set_dateAdded(getDate());
        productToBeCreated.set_category(String.valueOf(productCategory.getText()));
        productToBeCreated.set_productPrice(String.valueOf(productPrice.getText()));
        productToBeCreated.set_productPicture(BitmapConverter.convertFromBitmap(bitmapToSet.getValue()));

        DatabaseOperations databaseOperations = new DatabaseOperations(this);
        return databaseOperations.createNewProduct(productToBeCreated);
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void goBackToMainScreen() {
        NavigationUtils navUtils = new NavigationUtils();
        boolean isThisAdmin = getIntent().getBooleanExtra("isThisAdmin", false);
        navUtils.moveToMainListActivity(this, isThisAdmin);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == RC_PERMISSION) {
            if(allPermissionsGranted()) {
                startCamera();
            } else {
                ActivityCompat.requestPermissions(this, permissions, RC_PERMISSION);
            }
        }
    }

    private Boolean allPermissionsGranted() {
        boolean permissionGranted = false;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                permissionGranted = true;
            } else {
                permissionGranted = false;
                break;
            }
        }

        return permissionGranted;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_CAPTURE_IMAGE) {
            if(resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                viewModel.getBitmap().postValue(bitmap);
                viewModel.storeEncryptedBitmap();
            }
        }
    }

    private void startCamera() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.resolveActivity(this.getPackageManager());
        startActivityForResult(intent, RC_CAPTURE_IMAGE);
    }
}
