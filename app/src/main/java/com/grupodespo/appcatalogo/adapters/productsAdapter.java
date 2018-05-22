package com.grupodespo.appcatalogo.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grupodespo.appcatalogo.ProductDetailActivity;
import com.grupodespo.appcatalogo.ProductsListActivity;
import com.grupodespo.appcatalogo.R;
import com.grupodespo.appcatalogo.models.Product;

import java.util.List;

public class productsAdapter extends RecyclerView.Adapter<productsAdapter.ViewHolder> {
    private List<Product> products;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView productCardView;
        public TextView product_name;

        public ViewHolder(View v) {
            super(v);
            productCardView = (CardView) v.findViewById(R.id.product_card);
            product_name = (TextView) v.findViewById(R.id.product_name);
        }
    }

    @NonNull
    @Override
    public productsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_card, viewGroup, false);
        return new productsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull productsAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.product_name.setText(products.get(i).getName());
        Log.i("ProductAdapter", "setText: "  + products.get(i).getId());
        Log.i("ProductAdapter", "setText: "  + products.get(i).getCatid());
        Log.i("ProductAdapter", "setText: "  + products.get(i).getImage());
        Log.i("ProductAdapter", "setText: "  + products.get(i).getName());
        viewHolder.productCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("productSelectedId", products.get(i).getId());
                bundle.putString("productSelectedName", products.get(i).getName());
                Intent iconIntent = new Intent(view.getContext(), ProductDetailActivity.class);
                iconIntent.putExtras(bundle);
                view.getContext().startActivity(iconIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
    public productsAdapter(List<Product> products){
        this.products = products;
    }
    public List<Product> getItems(){
        return this.products;
    }

}
