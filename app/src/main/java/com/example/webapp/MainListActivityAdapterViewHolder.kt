package com.example.webapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.products_list_row.view.*

class MainListActivityAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setData(basket: Product, addToBasketClickListener: () -> Unit, rowClickListener: (Product) -> Unit) {

        itemView.rowProductName.text = basket.get_productName()
        itemView.rowProductShortDescription.text = basket.get_shortDescription()
        itemView.rowProductCategoryDescription.text = basket.get_category()
        itemView.rowProductCategoryPriceDescription.text = basket.get_productPrice().toString()

        itemView.rowProductCategoryAddToCartButton.setOnClickListener {

            addToBasketClickListener()
        }

        itemView.rowProductConatiner.setOnClickListener {

            rowClickListener(basket)
        }
    }
}