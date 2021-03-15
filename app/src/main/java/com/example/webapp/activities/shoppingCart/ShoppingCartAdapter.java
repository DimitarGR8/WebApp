package com.example.webapp.activities.shoppingCart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.webapp.R;
import com.example.webapp.data.model.Product;
import java.util.ArrayList;


public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartViewHolder> {
    ArrayList<Product> shoppingCartList;
    IShoppingCartActivity iShoppingCartActivity;

    public ShoppingCartAdapter(ArrayList<Product> shoppingCartList, IShoppingCartActivity iShoppingCartActivity) {
        this.shoppingCartList = shoppingCartList;
        this.iShoppingCartActivity = iShoppingCartActivity;
    }

    @NonNull
    @Override
    public ShoppingCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_cart_row, parent, false);
        return new ShoppingCartViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartViewHolder holder, int position) {
        Product product = shoppingCartList.get(position);
        holder.setData(product);

        holder.cartRowDeleteButton.setOnClickListener(view -> iShoppingCartActivity.onDeleteButtonClick(shoppingCartList.get(position)));
    }

    @Override
    public int getItemCount() {
        return shoppingCartList.size();
    }
}
