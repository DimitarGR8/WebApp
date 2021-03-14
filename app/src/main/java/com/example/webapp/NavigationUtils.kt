package com.example.webapp

import android.content.Context
import android.content.Intent

class NavigationUtils {
    fun moveToMainListActivity(context: Context, isThisAdmin: Boolean) {
        val mapActivityIntent = Intent(context, MainListActivity::class.java).apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            this.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
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
}