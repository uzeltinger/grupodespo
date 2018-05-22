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
import com.grupodespo.appcatalogo.models.Sale;

import java.util.List;

public class salesAdapter extends RecyclerView.Adapter<salesAdapter.ViewHolder>{
    private List<Sale> sales;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView saleCardView;
        public TextView sale_name, sale_date;
        public ViewHolder(View v) {
            super(v);
            saleCardView = (CardView) v.findViewById(R.id.sale_card);
            sale_name = (TextView) v.findViewById(R.id.sale_name);
            sale_date = (TextView) v.findViewById(R.id.sale_date);
        }
    }

    @NonNull
    @Override
    public salesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sale_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull salesAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.sale_name.setText(sales.get(position).getClientName());
        viewHolder.sale_date.setText(sales.get(position).getCreated());
        viewHolder.sale_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("saleSelectedId", sales.get(position).getId());
                bundle.putString("saleSelectedClientName", sales.get(position).getClientName());
                Intent iconIntent = new Intent(view.getContext(), SaleActivity.class);
                iconIntent.putExtras(bundle);
                view.getContext().startActivity(iconIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sales.size();
    }
    public salesAdapter(List<Sale> sales){
        this.sales = sales;
    }
    public List<Sale> getItems(){
        return this.sales;
    }


}
