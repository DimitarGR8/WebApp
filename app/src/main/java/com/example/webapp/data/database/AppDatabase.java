package com.example.webapp.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "ProductsDatabase";
    protected static final String TABLE_NAME = "products";

    protected static final String PRODUCT_ID = "productId";
    protected static final String PRODUCT_NAME = "productName";
    protected static final String PRODUCT_CATEGORY = "category";
    protected static final String PRODUCT_SHORT_DESCRIPTION = "shortDescription";
    protected static final String PRODUCT_MAIN_DESCRIPTION = "mainDescription";
    protected static final String PRODUCT_PRICE = "productPrice";
    protected static final String PRODUCT_DATE_ADDED = "dateAdded";
    protected static final String PRODUCT_PICTURE = "productPicture";

    public AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME  + "(" +
                PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PRODUCT_NAME + " TEXT, " +
                PRODUCT_CATEGORY + " TEXT, " +
                PRODUCT_SHORT_DESCRIPTION + " TEXT, " +
                PRODUCT_MAIN_DESCRIPTION + " TEXT, " +
                PRODUCT_PRICE + " TEXT, " +
                PRODUCT_DATE_ADDED + " TEXT, " +
                PRODUCT_PICTURE + " TEXT)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);

        onCreate(db);
    }
}
