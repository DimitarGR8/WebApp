package com.example.webapp.activities.shoppingCart;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.webapp.R;
import com.example.webapp.data.model.Product;
import com.example.webapp.utils.BitmapConverter;


public class ShoppingCartViewHolder extends RecyclerView.ViewHolder {

    ImageButton cartRowDeleteButton;
    TextView cartRowProductName;
    TextView cartRowProductCategoryDescription;
    TextView cartRowProductCategoryPriceDescription;
    ImageView cartRowProductImage;

     public ShoppingCartViewHolder(View itemView) {
        super(itemView);

        cartRowProductName = itemView.findViewById(R.id.cartRowProductName);
        cartRowProductCategoryDescription = itemView.findViewById(R.id.cartRowProductCategoryDescription);
        cartRowProductCategoryPriceDescription = itemView.findViewById(R.id.cartRowProductCategoryPriceDescription);
        cartRowDeleteButton = itemView.findViewById(R.id.cartRowDeleteButton);
        cartRowProductImage = itemView.findViewById(R.id.cartRowProductImage);
    }

    protected void setData(Product product) {
        cartRowProductName.setText(product.get_productName());
        cartRowProductCategoryDescription.setText(product.get_category());
        cartRowProductCategoryPriceDescription.setText(String.valueOf(product.get_productPrice()));
        cartRowProductImage.setImageBitmap(BitmapConverter.convertFromString(String.valueOf(product.get_productPicture())));
    }
}
