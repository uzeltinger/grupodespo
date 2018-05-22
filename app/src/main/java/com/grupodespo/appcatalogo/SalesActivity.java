package com.grupodespo.appcatalogo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.grupodespo.appcatalogo.adapters.salesAdapter;
import com.grupodespo.appcatalogo.helpers.AdminSQLiteOpenHelper;
import com.grupodespo.appcatalogo.models.Sale;
import java.util.ArrayList;
import java.util.List;

public class SalesActivity extends AppCompatActivity {
    private List<Sale> sales = new ArrayList();
    private List<Sale> salesList = new ArrayList();
    private RecyclerView salesRecyclerView;
    private RecyclerView.Adapter salesAdapter;
    private RecyclerView.LayoutManager salesLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        salesRecyclerView = (RecyclerView) findViewById(R.id.recycler_sales_list);
        salesRecyclerView.setHasFixedSize(true);
        salesLayoutManager = new LinearLayoutManager(this);
        salesRecyclerView.setLayoutManager(salesLayoutManager);
        salesAdapter = new salesAdapter(sales);
        salesRecyclerView.setAdapter(salesAdapter);
        int saleSelectedId = 0;
        getSales(saleSelectedId);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSales(view);
            }
        });
    }

    private void deleteSales(View view) {
        Log.i("MAIN", "deleteSales: "  + salesList.size());
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(SalesActivity.this,null,null,0);
        db.deleteSales();
        SharedPreferences preferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("clienteName", "");
        editor.commit();
        Toast.makeText(this, "Pedidos eliminados.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    private void getSales(int categorySelectedId) {
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(SalesActivity.this,null,null,0);
        salesList = db.getSales();
        Log.i("MAIN", "salesList: "  + salesList.size());
        for(int i=0; i<salesList.size(); i++) {
            //Log.i("MAIN", "categoriesList: "  + categoriesList.get(i));
            sales.add(salesList.get(i));
        }
        if(salesList.size()==0 || sales.size()==0) {
            Toast.makeText(this, "No hay ventas guardadas.", Toast.LENGTH_SHORT).show();
        }
    }

}
