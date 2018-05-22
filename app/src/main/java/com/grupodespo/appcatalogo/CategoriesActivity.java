package com.grupodespo.appcatalogo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.grupodespo.appcatalogo.adapters.categoriesAdapter;
import com.grupodespo.appcatalogo.helpers.AdminSQLiteOpenHelper;
import com.grupodespo.appcatalogo.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {
    private List<Category> categories = new ArrayList();
    private List<Category> categoriesList = new ArrayList();
    private RecyclerView categoriesRecyclerView;
    private RecyclerView.Adapter categoriesAdapter;
    private RecyclerView.LayoutManager categoriesLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        categoriesRecyclerView = (RecyclerView) findViewById(R.id.recycler_category_list);
        categoriesRecyclerView.setHasFixedSize(true);
        categoriesLayoutManager = new LinearLayoutManager(this);
        categoriesRecyclerView.setLayoutManager(categoriesLayoutManager);
        categoriesAdapter = new categoriesAdapter(categories);
        categoriesRecyclerView.setAdapter(categoriesAdapter);
        int categorySelectedId = 0;
        getCategories(categorySelectedId);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCart();
            }
        });
    }

    private void viewCart() {
        Intent intent = new Intent(this, SaleActivity.class);
        this.startActivity(intent);
    }

    private void getCategories(int categorySelectedId) {
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(CategoriesActivity.this,null,null,0);
        categoriesList = db.getCategories(0);
        Log.i("MAIN", "categoriesList: "  + categoriesList.size());
        for(int i=0; i<categoriesList.size(); i++) {
            //Log.i("MAIN", "categoriesList: "  + categoriesList.get(i));
            categories.add(categoriesList.get(i));
        }
        if(categoriesList.size()==0 || categories.size()==0) {
            Toast.makeText(this, "No hay categorías guardadas. Por favor ejecute la descarga.", Toast.LENGTH_SHORT).show();
        }
    }

}
