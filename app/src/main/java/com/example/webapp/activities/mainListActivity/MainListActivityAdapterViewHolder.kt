package com.example.webapp.activities.mainListActivity

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.webapp.utils.BitmapConverter
import com.example.webapp.data.model.Product
import kotlinx.android.synthetic.main.products_list_row.view.*

class MainListActivityAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setData(product: Product, addToBasketClickListener: (Product) -> Unit, rowClickListener: (Product) -> Unit) {

        itemView.rowProductName.text = product.get_productName()
        itemView.rowProductShortDescription.text = product.get_shortDescription()
        itemView.rowProductCategoryDescription.text = product.get_category()
        itemView.rowProductCategoryPriceDescription.text = product.get_productPrice().toString()

        itemView.rowProductImage.setImageBitmap(BitmapConverter.convertFromString(product.get_productPicture()))

        itemView.rowProductCategoryAddToCartButton.setOnClickListener {

            addToBasketClickListener(product)
        }

        itemView.rowProductConatiner.setOnClickListener {

            rowClickListener(product)
        }
    }
}