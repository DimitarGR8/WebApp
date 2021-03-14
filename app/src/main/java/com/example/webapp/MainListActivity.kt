package com.example.webapp

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main_list.*

class MainListActivity : BaseActivity(), View.OnClickListener {

    private val addToBasketClickListener: (() -> Unit) = {

    }

    private val rowClickListener: ((Product) -> Unit) = {
        NavigationUtils().moveToProductActivity(this, it, isThisAdmin)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)

        setupAdapter()

        listScreenNewProductButton.setOnClickListener(this)
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
        }
    }
}