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
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.grupodespo.appcatalogo.adapters.productsAdapter;
import com.grupodespo.appcatalogo.adapters.subcategoriesAdapter;
import com.grupodespo.appcatalogo.helpers.AdminSQLiteOpenHelper;
import com.grupodespo.appcatalogo.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsListActivity extends AppCompatActivity {
    private List<Product> products = new ArrayList();
    private List<Product> prodyuctsList = new ArrayList();
    private RecyclerView productsRecyclerView;
    private RecyclerView.Adapter prodAdapter;
    private RecyclerView.LayoutManager productsLayoutManager;
    public int subcategorySelectedId = 0;
    public String subcategorySelectedName;
    SharedPreferences preferencias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("subcategorySelectedId")) {
                subcategorySelectedId = extras.getInt("subcategorySelectedId");
                escribirPreferencias("subcategorySelectedId",String.valueOf(subcategorySelectedId));
            }else{
                subcategorySelectedId = Integer.parseInt(preferencias.getString("subcategorySelectedId",""));
            }
            if (extras.containsKey("subcategorySelectedName")) {
                subcategorySelectedName = extras.getString("subcategorySelectedName");
                escribirPreferencias("subcategorySelectedName",subcategorySelectedName);
            }else{
                subcategorySelectedName = preferencias.getString("subcategorySelectedName","Sub Categorías");
            }

        }else{
            subcategorySelectedId = Integer.parseInt(preferencias.getString("subcategorySelectedId",""));
            subcategorySelectedName = preferencias.getString("subcategorySelectedName","Sub Categorías");
        }
        this.setTitle(subcategorySelectedName);

        productsRecyclerView = (RecyclerView) findViewById(R.id.recycler_products_list);
        productsRecyclerView.setHasFixedSize(true);
        productsLayoutManager = new LinearLayoutManager(this);
        productsRecyclerView.setLayoutManager(productsLayoutManager);
        prodAdapter = new productsAdapter(products);
        productsRecyclerView.setAdapter(prodAdapter);
        getProductsList(subcategorySelectedId);
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
    private void getProductsList(int subcategorySelectedId) {
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(ProductsListActivity.this,null,null,0);
        prodyuctsList = db.getProducts(subcategorySelectedId);
        for(int i=0; i<prodyuctsList.size(); i++) {
            //Log.i("MAIN", "categoriesList: "  + categories.get(i));
            products.add(prodyuctsList.get(i));
        }
        if(products.size()>0) {
            //Toast.makeText(this, "Subcategorías cargadas", Toast.LENGTH_SHORT).show();
            //textViewWelcome.setText("");
        }else{
            Toast.makeText(this, "No hay productos para esta categoría", Toast.LENGTH_SHORT).show();
        }
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Boolean retornar = super.onOptionsItemSelected(menuItem);
        if (menuItem.getItemId() == android.R.id.home) {
            Toast.makeText(this, "Home pressed", Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putInt("subcategorySelectedId", subcategorySelectedId);
            bundle.putString("subcategorySelectedName", subcategorySelectedName);
            Intent intent = new Intent(ProductsListActivity.this, SubcategoriesActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            retornar = true;
            return true;
        }
        return retornar;
    }
*/
    private void escribirPreferencias(String name, String value){
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(name, value);
        editor.commit();
    }

}
