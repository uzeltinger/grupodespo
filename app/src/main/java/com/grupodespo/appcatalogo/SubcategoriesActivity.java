package com.grupodespo.appcatalogo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.grupodespo.appcatalogo.adapters.categoriesAdapter;
import com.grupodespo.appcatalogo.adapters.subcategoriesAdapter;
import com.grupodespo.appcatalogo.helpers.AdminSQLiteOpenHelper;
import com.grupodespo.appcatalogo.models.Category;

import java.util.ArrayList;
import java.util.List;

public class SubcategoriesActivity extends AppCompatActivity {
    private List<Category> subcategories = new ArrayList();
    private List<Category> categoriesList = new ArrayList();
    private RecyclerView categoriesRecyclerView;
    private RecyclerView.Adapter subcategoriesAdapter;
    private RecyclerView.LayoutManager categoriesLayoutManager;
    public int categorySelectedId;
    public String categorySelectedName;
    SharedPreferences preferencias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        categorySelectedId = 0;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("categorySelectedId")) {
                categorySelectedId = extras.getInt("categorySelectedId");
                escribirPreferencias("categorySelectedId",String.valueOf(categorySelectedId));
            }else{
                categorySelectedId = Integer.parseInt(preferencias.getString("categorySelectedId",""));
            }
            if (extras.containsKey("categorySelectedName")) {
                categorySelectedName = extras.getString("categorySelectedName");
                escribirPreferencias("categorySelectedName",categorySelectedName);
            }else{
                categorySelectedName = preferencias.getString("categorySelectedName","Subcategorías");
            }

        }else{
            categorySelectedId = Integer.parseInt(preferencias.getString("categorySelectedId",""));
            categorySelectedName = preferencias.getString("categorySelectedName","Subcategorías");
        }
        this.setTitle(categorySelectedName);
        Log.d("Subcategorias"," categorySelectedId : " + categorySelectedId);
        Log.d("Subcategorias"," categorySelectedName : " + categorySelectedName);

        categoriesRecyclerView = (RecyclerView) findViewById(R.id.recycler_category_list);
        categoriesRecyclerView.setHasFixedSize(true);
        categoriesLayoutManager = new LinearLayoutManager(this);
        categoriesRecyclerView.setLayoutManager(categoriesLayoutManager);
        subcategoriesAdapter = new subcategoriesAdapter(subcategories);
        categoriesRecyclerView.setAdapter(subcategoriesAdapter);
        getSubCategories(categorySelectedId);
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
    private void getSubCategories(int categorySelectedId) {
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(SubcategoriesActivity.this,null,null,0);
        categoriesList = db.getCategories(categorySelectedId);
        for(int i=0; i<categoriesList.size(); i++) {
            //Log.i("MAIN", "categoriesList: "  + categories.get(i));
            subcategories.add(categoriesList.get(i));
        }
        if(subcategories.size()>0) {
            //Toast.makeText(this, "Subcategorías cargadas", Toast.LENGTH_SHORT).show();
            //textViewWelcome.setText("");
        }else{
            Toast.makeText(this, "No hay subcategorías para esta categoría", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            Toast.makeText(this, "Home pressed", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void escribirPreferencias(String name, String value){
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(name, value);
        editor.commit();
    }

}
