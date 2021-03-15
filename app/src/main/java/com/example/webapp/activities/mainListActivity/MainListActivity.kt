package com.example.webapp.activities.mainListActivity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.webapp.*
import com.example.webapp.activities.baseActivity.BaseActivity
import com.example.webapp.data.database.DatabaseOperations
import com.example.webapp.data.model.Product
import com.example.webapp.utils.NavigationUtils
import kotlinx.android.synthetic.main.activity_main_list.*

class MainListActivity : BaseActivity(), View.OnClickListener {

    private var productsInBacket: ArrayList<Product> = arrayListOf()

    private val addToBasketClickListener: ((Product) -> Unit) = {
        if(!productsInBacket.contains(it)) {
            productsInBacket.add(it)
            listScreenCartCounter.text = productsInBacket.count().toString()
        }
    }

    private val rowClickListener: ((Product) -> Unit) = {
        NavigationUtils().moveToProductActivity(this, it, isThisAdmin)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)

        initViews()
        setupAdapter()

        listScreenNewProductButton.setOnClickListener(this)
        listScreenCartImage.setOnClickListener(this)
    }

    private fun initViews() {
        listScreenCartCounter.text = productsInBacket.count().toString()
    }

    private fun setupAdapter() {
        val productsList = DatabaseOperations(this).allProducts

        listScreenRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        listScreenRecyclerView.adapter = MainListActivityAdapter(productsList, addToBasketClickListener, rowClickListener)
    }

    override fun onClick(view: View?) {
        when (view) {
            listScreenNewProductButton -> {
                NavigationUtils().moveToCreateProductActivity(this, isThisAdmin)
            }
            listScreenCartImage -> {
                if(productsInBacket.isNullOrEmpty()) {
                    Toast.makeText(this, "Your cart is empty. Please select products.", Toast.LENGTH_LONG).show()
                } else {
                    NavigationUtils().moveToShoppingCartActivity(this, isThisAdmin, productsInBacket)
                }
            }
        }
    }
}