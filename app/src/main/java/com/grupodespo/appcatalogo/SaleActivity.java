package com.grupodespo.appcatalogo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import com.grupodespo.appcatalogo.adapters.saleItemsAdapter;
import com.grupodespo.appcatalogo.helpers.AdminSQLiteOpenHelper;
import com.grupodespo.appcatalogo.models.SaleItem;
import java.util.ArrayList;
import java.util.List;

public class SaleActivity extends AppCompatActivity {
    private List<SaleItem> saleItems = new ArrayList();
    private List<SaleItem> saleItemsList = new ArrayList();
    private RecyclerView saleItemsRecyclerView;
    private RecyclerView.Adapter saleItemsAdapter;
    private RecyclerView.LayoutManager saleItemsLayoutManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saleItemsRecyclerView = (RecyclerView) findViewById(R.id.recycler_sale_list);
        saleItemsRecyclerView.setHasFixedSize(true);
        saleItemsLayoutManager = new LinearLayoutManager(this);
        saleItemsRecyclerView.setLayoutManager(saleItemsLayoutManager);
        saleItemsAdapter = new saleItemsAdapter(saleItems);
        saleItemsRecyclerView.setAdapter(saleItemsAdapter);
        int saleSelectedId = 0;
        String saleSelectedClientName;

        SharedPreferences preferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        saleSelectedClientName = preferencias.getString("clienteName","");
        saleSelectedId = Integer.parseInt(preferencias.getString("saleId",""));
        this.setTitle("Pedido de: " + saleSelectedClientName);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("saleSelectedId")) {
                saleSelectedId = extras.getInt("saleSelectedId");
            }
            if (extras.containsKey("saleSelectedClientName")) {
                saleSelectedClientName = extras.getString("saleSelectedClientName");
                this.setTitle("Pedido de: " + saleSelectedClientName);
            }
        }
        getSaleItems(saleSelectedId);
    }

    private void getSaleItems(int saleSelectedId) {
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(SaleActivity.this,null,null,0);
        saleItemsList = db.getSaleItems(saleSelectedId);
        Log.i("SaleActivity", "getSaleItems: "  + saleItemsList.size());
        for(int i=0; i<saleItemsList.size(); i++) {
            //Log.i("MAIN", "categoriesList: "  + categoriesList.get(i));
            saleItems.add(saleItemsList.get(i));
        }
        if(saleItemsList.size()==0 || saleItems.size()==0) {
            Toast.makeText(this, "No hay items en esta venta guardados.", Toast.LENGTH_SHORT).show();
        }
    }

}
