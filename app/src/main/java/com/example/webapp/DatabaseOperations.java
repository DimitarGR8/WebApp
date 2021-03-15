package com.example.webapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseOperations extends AppDatabase{
    public DatabaseOperations(Context context) {
        super(context);
    }

    public boolean createNewProduct(Product product) {
        ContentValues productValues = new ContentValues();
        productValues.put("productName", product.get_productName());
        productValues.put("shortDescription", product.get_shortDescription());
        productValues.put("mainDescription", product.get_mainDescription());
        productValues.put("dateAdded", product.get_dateAdded());
        productValues.put("productPrice", product.get_productPrice());
        productValues.put("category", product.get_category());
        productValues.put("productPicture", product.get_category());

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert(TABLE_NAME, null, productValues) > 0;
        db.close();

        return createSuccessful;
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> contactList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product basket = new Product();
                basket.set_productId(Integer.parseInt(cursor.getString(0)));
                basket.set_productName(cursor.getString(1));
                basket.set_category(cursor.getString(2));
                basket.set_shortDescription(cursor.getString(3));
                basket.set_mainDescription(cursor.getString(4));
                basket.set_productPrice(cursor.getDouble(5));
                basket.set_dateAdded(cursor.getString(6));
                basket.set_productPicture(cursor.getString(7));

                // Adding product to list
                contactList.add(basket);
            } while (cursor.moveToNext());
        }

        // Return products list
        return contactList;
    }

    public boolean updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues productValues = new ContentValues();
        productValues.put("productId", product.get_productId());
        productValues.put("productName", product.get_productName());
        productValues.put("shortDescription", product.get_shortDescription());
        productValues.put("mainDescription", product.get_mainDescription());
        productValues.put("dateAdded", product.get_dateAdded());
        productValues.put("productPrice", product.get_productPrice());
        productValues.put("category", product.get_category());
        productValues.put("productPicture", product.get_productPicture());

        // Updating row
        boolean isSuccessful = db.update(TABLE_NAME, productValues, PRODUCT_ID + " = " + product.get_productId(), null) > 0;
        db.close();
        return isSuccessful;
    }

    // Deleting single product
    public boolean deleteProduct(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isSuccessful = db.delete(TABLE_NAME, PRODUCT_ID + " = " + productId, null) > 0;
        db.close();
        return isSuccessful;
    }
}
