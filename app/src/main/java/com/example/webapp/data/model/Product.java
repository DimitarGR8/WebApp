package com.example.webapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    int _productId;
    String _productName;
    String _category;
    String _shortDescription;
    String _mainDescription;
    String _productPrice;
    String _dateAdded;
    String _productPicture;

    public Product(int productId,
                   String productName,
                   String category,
                   String shortDescription,
                   String mainDescription,
                   String productPrice,
                   String dateAdded,
                   String productPicture) {

        this._productId = productId;
        this._productName = productName;
        this._category = category;
        this._shortDescription = shortDescription;
        this._mainDescription = mainDescription;
        this._productPrice = productPrice;
        this._dateAdded = dateAdded;
        this._productPicture = productPicture;
    }

    public Product() {

    }

    protected Product(Parcel in) {
        _productId = in.readInt();
        _productName = in.readString();
        _category = in.readString();
        _shortDescription = in.readString();
        _mainDescription = in.readString();
        _productPrice = in.readString();
        _dateAdded = in.readString();
        _productPicture = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public int get_productId() {
        return _productId;
    }

    public void set_productId(int _productId) {
        this._productId = _productId;
    }

    public String get_productName() {
        return _productName;
    }

    public void set_productName(String _productName) {
        this._productName = _productName;
    }

    public String get_category() {
        return _category;
    }

    public void set_category(String _category) {
        this._category = _category;
    }

    public String get_shortDescription() {
        return _shortDescription;
    }

    public void set_shortDescription(String _shortDescription) {
        this._shortDescription = _shortDescription;
    }

    public String get_mainDescription() {
        return _mainDescription;
    }

    public void set_mainDescription(String _mainDescription) {
        this._mainDescription = _mainDescription;
    }

    public String get_productPrice() {
        return _productPrice;
    }

    public void set_productPrice(String _productPrice) {
        this._productPrice = _productPrice;
    }

    public String get_dateAdded() {
        return _dateAdded;
    }

    public void set_dateAdded(String _dateAdded) {
        this._dateAdded = _dateAdded;
    }

    public String get_productPicture() {
        return _productPicture;
    }

    public void set_productPicture(String _productPicture) {
        this._productPicture = _productPicture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(_productId);
        parcel.writeString(_productName);
        parcel.writeString(_category);
        parcel.writeString(_shortDescription);
        parcel.writeString(_mainDescription);
        parcel.writeString(_productPrice);
        parcel.writeString(_dateAdded);
        parcel.writeString(_productPicture);
    }
}
