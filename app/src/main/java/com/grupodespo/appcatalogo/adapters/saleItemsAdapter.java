package com.grupodespo.appcatalogo.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grupodespo.appcatalogo.R;
import com.grupodespo.appcatalogo.SaleActivity;
import com.grupodespo.appcatalogo.models.SaleItem;
import com.grupodespo.appcatalogo.models.Sale;

import java.util.List;

public class saleItemsAdapter extends RecyclerView.Adapter<saleItemsAdapter.ViewHolder>{

    private List<SaleItem> saleItems;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView saleItemCardView;
        public TextView sale_item_name;
        public ViewHolder(View v) {
            super(v);
            saleItemCardView = (CardView) v.findViewById(R.id.sale_item_card);
            sale_item_name = (TextView) v.findViewById(R.id.sale_item_name);
        }
    }

    @NonNull
    @Override
    public saleItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sale_item_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull saleItemsAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.sale_item_name.setText(saleItems.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return saleItems.size();
    }
    public saleItemsAdapter(List<SaleItem> saleItems){
        this.saleItems = saleItems;
    }
    public List<SaleItem> getItems(){
        return this.saleItems;
    }


}
