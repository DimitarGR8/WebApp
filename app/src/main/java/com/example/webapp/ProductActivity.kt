package com.example.webapp

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity: BaseActivity(), View.OnClickListener {

    private var prodcutIdText = 0
    private lateinit var productNameText: String
    private lateinit var productCategoryText: String
    private lateinit var productShortDescriptionText: String
    private lateinit var productLongDescriptionText: String
    private lateinit var productAddedDateText: String
    private var productPriceText = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        if(intent.getBooleanExtra("isThisAdmin", false)) {
            productSaveNewDataButton.visibility = View.VISIBLE
            productDeleteButton.visibility = View.VISIBLE
        } else {
            productSaveNewDataButton.visibility = View.GONE
            productDeleteButton.visibility = View.GONE
        }

        setProductData()

        productSaveNewDataButton.setOnClickListener(this)
        productBackButton.setOnClickListener(this)
        productDeleteButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            productSaveNewDataButton -> {
                if (updateProduct()) {
                    goBackToMainListActivity()
                }
            }
            productBackButton -> {
                goBackToMainListActivity()
            }
            productDeleteButton -> {
                if (deleteProduct()) {
                    goBackToMainListActivity()
                }
            }
        }
    }

    private fun setProductData() {
        prodcutIdText = intent.getIntExtra("productId", 0)
        productNameText = intent.getStringExtra("productName").toString()
        productCategoryText = intent.getStringExtra("productCategory").toString()
        productShortDescriptionText = intent.getStringExtra("productShortDescription").toString()
        productLongDescriptionText = intent.getStringExtra("productLongDescription").toString()
        productAddedDateText = intent.getStringExtra("productAddedDate").toString()
        productPriceText = intent.getDoubleExtra("productPrice", 00.00)

        productName.setText(productNameText)
        productCategory.text = productCategoryText
        productShortDescription.setText(productShortDescriptionText)
        productLongDescription.setText(productLongDescriptionText)
        productDateAdded.text = productAddedDateText
        productPrice.setText(productPriceText.toString())
    }

    private fun updateProduct() : Boolean {
        val productToUpdate = Product(prodcutIdText,
            productName.text.toString(),
            productCategory.text.toString(),
            productShortDescription.text.toString(),
            productLongDescription.text.toString(),
            productPrice.text.toString().toDouble(),
            productDateAdded.text.toString(),
            "" )
        return DatabaseOperations(this).updateProduct(productToUpdate)
    }

    private fun deleteProduct () : Boolean {
        return DatabaseOperations(this).deleteProduct(prodcutIdText)
    }

    private fun goBackToMainListActivity() {
        NavigationUtils().moveToMainListActivity(this, isThisAdmin)
    }
}