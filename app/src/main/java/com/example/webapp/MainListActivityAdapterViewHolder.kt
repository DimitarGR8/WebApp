package com.example.webapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.products_list_row.view.*

class MainListActivityAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setData(product: Product, addToBasketClickListener: () -> Unit, rowClickListener: (Product) -> Unit) {

        itemView.rowProductName.text = product.get_productName()
        itemView.rowProductShortDescription.text = product.get_shortDescription()
        itemView.rowProductCategoryDescription.text = product.get_category()
        itemView.rowProductCategoryPriceDescription.text = product.get_productPrice().toString()

        val aa = product.get_productPicture()
        itemView.rowProductImage.setImageBitmap(BitmapConverter.convertFromString(product.get_productPicture()))

        itemView.rowProductCategoryAddToCartButton.setOnClickListener {

            addToBasketClickListener()
        }

        itemView.rowProductConatiner.setOnClickListener {

            rowClickListener(product)
        }
    }
}