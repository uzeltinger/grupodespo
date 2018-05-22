package com.grupodespo.appcatalogo;

import android.app.Dialog;
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
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.grupodespo.appcatalogo.adapters.categoriesAdapter;

import com.grupodespo.appcatalogo.helpers.AdminSQLiteOpenHelper;
import com.grupodespo.appcatalogo.models.Category;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static MainActivity mContext;
    Dialog ThisDialog;
    /*
    private List<Category> categories = new ArrayList();
    private List<Category> categoriesList = new ArrayList();
    private RecyclerView categoriesRecyclerView;
    private RecyclerView.Adapter categoriesAdapter;
    private RecyclerView.LayoutManager categoriesLayoutManager;
    */
    private TextView textViewWelcome;
    private Button buttonCloseClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        categoriesRecyclerView = (RecyclerView) findViewById(R.id.recycler_category_list);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        categoriesRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        categoriesLayoutManager = new LinearLayoutManager(this);
        categoriesRecyclerView.setLayoutManager(categoriesLayoutManager);

        // specify an adapter (see also next example)
        categoriesAdapter = new categoriesAdapter(categories);
        categoriesRecyclerView.setAdapter(categoriesAdapter);

        int categorySelectedId = 0;
        getCategories(categorySelectedId);
*/
        textViewWelcome = (TextView) findViewById(R.id.textViewWelcome);
        SharedPreferences preferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        buttonCloseClient = (Button) findViewById(R.id.buttonCloseClient);

        String clienteName = preferencias.getString("clienteName","");
        if(clienteName.length()>0){
            textViewWelcome.setText("Hay un cliente Abierto: " + clienteName);
            buttonCloseClient.setVisibility(View.VISIBLE);
        }else{
            textViewWelcome.setText("Catálogo de Grupo Despo.");
            buttonCloseClient.setVisibility(View.GONE);
        }
        buttonCloseClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSale(v);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSale(view);
            }
        });
        Log.i("MAIN", "preferencias: "  + preferencias.getString("url",""));

    }
    public static MainActivity getContext() {
        //MainActivity.getContext()
        return mContext;
    }
/*
    private void getCategories(int categorySelectedId) {
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(MainActivity.this,null,null,0);
        categoriesList = db.getCategories(0);
        Log.i("MAIN", "categoriesList: "  + categoriesList.size());
        for(int i=0; i<categoriesList.size(); i++) {
            //Log.i("MAIN", "categoriesList: "  + categoriesList.get(i));
            categories.add(categoriesList.get(i));
        }
        if(categories.size()>0) {
            textViewWelcome.setText("");
        }
        //categoriesAdapter.notifyDataSetChanged();
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, ConfigurationActivity.class);
            this.startActivity(intent);
            return true;
        }
        if (id == R.id.action_sales) {
            Intent intent = new Intent(this, SalesActivity.class);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void addSale(View view) {
        SharedPreferences preferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        String clienteName = preferencias.getString("clienteName","");
        if(clienteName.length()==0){
            Snackbar.make(view, "Debe asignar un cliente", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            showClientDialog(view);
        }else{
            Intent intent = new Intent(this, CategoriesActivity.class);
            this.startActivity(intent);
        }

    }
    private void closeSale(View view) {
        buttonCloseClient.setVisibility(View.GONE);
        saveClientPreferences(view,"");
        textViewWelcome.setText("Catálogo de Grupo Despo.");
    }

    private void showClientDialog(View view){
        ThisDialog = new Dialog(MainActivity.this);
        ThisDialog.setTitle("Save Your Name");
        ThisDialog.setContentView(R.layout.dialog_template);
        final EditText textClienteName = (EditText)ThisDialog.findViewById(R.id.textClienteName);
        Button SaveClientName = (Button)ThisDialog.findViewById(R.id.SaveClientName);
        textClienteName.setEnabled(true);
        SaveClientName.setEnabled(true);
        SaveClientName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textClienteName.getText().toString();
                if(name.length()>0) {
                    saveClientPreferences(v, textClienteName.getText().toString());
                    addNewSale(name);
                    buttonCloseClient.setVisibility(View.VISIBLE);
                    ThisDialog.cancel();
                }
            }
        });
        ThisDialog.show();
    }

    private void addNewSale(String name) {
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(MainActivity.this,null,null,0);
        long saleId = db.addNewSale(name);
        saveSaleIdPreferences(saleId);
        Toast.makeText(this, "Inicializada la venta : " + saleId + " a " + name, Toast.LENGTH_SHORT).show();
    }

    private void saveSaleIdPreferences(long saleId) {
        SharedPreferences preferencias = getSharedPreferences("preferencias",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("saleId", String.valueOf(saleId));
        editor.commit();
    }

    private void saveClientPreferences(View view, String name){
        SharedPreferences preferencias = getSharedPreferences("preferencias",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("clienteName", name);
        editor.commit();
        textViewWelcome.setText("Hay un cliente Abierto: " + name);
        //finish();
    }
}
