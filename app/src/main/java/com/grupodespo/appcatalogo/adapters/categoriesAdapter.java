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
import com.grupodespo.appcatalogo.SubcategoriesActivity;
import com.grupodespo.appcatalogo.models.Category;

import java.util.List;

public class categoriesAdapter extends RecyclerView.Adapter<categoriesAdapter.ViewHolder>  {
    private List<Category> categories;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView categoryCardView;
        public TextView cat_name;
        public ViewHolder(View v) {
            super(v);
            categoryCardView = (CardView) v.findViewById(R.id.category_card);
            cat_name = (TextView) v.findViewById(R.id.cat_name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.category_card, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.cat_name.setText(categories.get(position).getName());
        viewHolder.cat_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("categorySelectedId", categories.get(position).getId());
                bundle.putString("categorySelectedName", categories.get(position).getName());
                Intent iconIntent = new Intent(view.getContext(), SubcategoriesActivity.class);
                iconIntent.putExtras(bundle);
                view.getContext().startActivity(iconIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
    public categoriesAdapter(List<Category> categories){
        this.categories = categories;
    }
    public List<Category> getItems(){
        return this.categories;
    }

}
