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
        ArrayList<Product> contactList = new ArrayList<Product>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
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

                // Adding contact to list
                contactList.add(basket);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public Boolean updateContact(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues productValues = new ContentValues();
        productValues.put("productId", product.get_productId());
        productValues.put("productName", product.get_productName());
        productValues.put("shortDescription", product.get_shortDescription());
        productValues.put("mainDescription", product.get_mainDescription());
        productValues.put("dateAdded", product.get_dateAdded());
        productValues.put("productPrice", product.get_productPrice());
        productValues.put("category", product.get_category());
        productValues.put("shortDescription", product.get_productPicture());

        // updating row
        ///test

        //database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + product.get_productId(), null);
        return db.update(TABLE_NAME, productValues, PRODUCT_ID + " =? " + product.get_productId(), null) > 1;
               // new String[] { String.valueOf(product.get_productId()) }) > 1;
    }

    // Deleting single contact
    public void deleteContact(Product basket) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, PRODUCT_ID + " = ?",
                new String[] { String.valueOf(basket.get_productId()) });
        db.close();
    }
}
