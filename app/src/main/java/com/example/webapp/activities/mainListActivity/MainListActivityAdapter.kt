package com.example.webapp.activities.mainListActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.webapp.data.model.Product
import com.example.webapp.R

class MainListActivityAdapter(var myDataSet: ArrayList<Product>,
                              private val addToBacketClickListener: (Product) -> Unit,
                              private val rowClickListener:(Product) -> Unit) : RecyclerView.Adapter<MainListActivityAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListActivityAdapterViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.products_list_row, parent, false)
        return MainListActivityAdapterViewHolder(mView)
    }

    override fun onBindViewHolder(holder: MainListActivityAdapterViewHolder, position: Int) {
        if(!myDataSet.indices.contains(position)) {
            return
        }

        val product = myDataSet[position]
        holder.setData(product,
            addToBasketClickListener = {
                addToBacketClickListener.invoke(it)
        },
            rowClickListener = {
                rowClickListener.invoke(it)
        })
    }

    override fun getItemCount(): Int {
        return myDataSet.count()
    }
}