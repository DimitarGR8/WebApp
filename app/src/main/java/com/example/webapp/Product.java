package com.example.webapp;

import java.io.ByteArrayOutputStream;

public class Product {

    int _productId;
    String _productName;
    String _category;
    String _shortDescription;
    String _mainDescription;
    Double _productPrice;
    String _dateAdded;
    ByteArrayOutputStream _productPicture;

    public Product(int productId,
                   String productName,
                   String category,
                   String shortDescription,
                   String mainDescription,
                   Double productPrice,
                   String dateAdded,
                   ByteArrayOutputStream productPicture) {

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

    public Double get_productPrice() {
        return _productPrice;
    }

    public void set_productPrice(Double _productPrice) {
        this._productPrice = _productPrice;
    }

    public String get_dateAdded() {
        return _dateAdded;
    }

    public void set_dateAdded(String _dateAdded) {
        this._dateAdded = _dateAdded;
    }

    public ByteArrayOutputStream get_productPicture() {
        return _productPicture;
    }

    public void set_productPicture(ByteArrayOutputStream _productPicture) {
        this._productPicture = _productPicture;
    }
}
