package com.example.webapp.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.webapp.activities.checkoutActivity.CheckoutActivity
import com.example.webapp.activities.createProductActivity.CreateProductActivity
import com.example.webapp.activities.mainListActivity.MainListActivity
import com.example.webapp.activities.productActivity.ProductActivity
import com.example.webapp.activities.shoppingCart.ShoppingCartActivity
import com.example.webapp.data.model.Product


class NavigationUtils {

    fun moveToMainListActivity(context: Context, isThisAdmin: Boolean) {
        val mapActivityIntent = Intent(context, MainListActivity::class.java).apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            this.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            this.putExtra("isThisAdmin", isThisAdmin)
        }

        context.startActivity(mapActivityIntent)
    }

    fun moveToMainListActivityWithNoHistory(context: Context, isThisAdmin: Boolean) {
        val mapActivityIntent = Intent(context, MainListActivity::class.java).apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            this.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            this.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            this.putExtra("isThisAdmin", isThisAdmin)
        }

        context.startActivity(mapActivityIntent)
    }

    fun moveToProductActivity(context: Context, basket: Product, isThisAdmin: Boolean) {
        val mapActivityIntent = Intent(context, ProductActivity::class.java).apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            this.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            this.putExtra("productId", basket.get_productId())
            this.putExtra("productName", basket.get_productName())
            this.putExtra("productCategory", basket.get_category())
            this.putExtra("productShortDescription", basket.get_shortDescription())
            this.putExtra("productLongDescription", basket.get_mainDescription())
            this.putExtra("productAddedDate", basket.get_dateAdded())
            this.putExtra("productPrice", basket.get_productPrice())
            this.putExtra("productPicture", basket.get_productPicture())
            this.putExtra("isThisAdmin", isThisAdmin)
        }

        context.startActivity(mapActivityIntent)
    }

    fun moveToCreateProductActivity(context: Context, isThisAdmin: Boolean) {
        val mapActivityIntent = Intent(context, CreateProductActivity::class.java).apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            this.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            this.putExtra("isThisAdmin", isThisAdmin)
        }

        context.startActivity(mapActivityIntent)
    }

    fun moveToShoppingCartActivity(context: Context, isThisAdmin: Boolean, shoppingCartList: ArrayList<Product>) {
        val mapActivityIntent = Intent(context, ShoppingCartActivity::class.java).apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            this.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            val bundle = Bundle()
            bundle.putParcelableArrayList("shoppingCartList", shoppingCartList)
            this.putExtras(bundle)
            this.putExtra("isThisAdmin", isThisAdmin)
        }

        context.startActivity(mapActivityIntent)
    }

    fun moveToCheckoutActivity(context: Context, isThisAdmin: Boolean, finalPrice: Double) {
        val mapActivityIntent = Intent(context, CheckoutActivity::class.java).apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            this.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            this.putExtra("finalPrice", finalPrice)
            this.putExtra("isThisAdmin", isThisAdmin)
        }

        context.startActivity(mapActivityIntent)
    }
}
