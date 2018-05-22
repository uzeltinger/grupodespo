package com.grupodespo.appcatalogo;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.grupodespo.appcatalogo.helpers.AdminSQLiteOpenHelper;
import com.grupodespo.appcatalogo.models.Product;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ProductDetailActivity extends AppCompatActivity {
    public int subcategorySelectedId = 0;
    public String subcategorySelectedName;
    SharedPreferences preferencias;
    int productSelectedId = 0;
    String productSelectedName;
    Product productDetail;
    TextView detailTitle;
    ImageView detailImage;
    TextView detailText;
    String saleId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        saleId = preferencias.getString("saleId","");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("productSelectedId")) {
                productSelectedId = extras.getInt("productSelectedId");
            }
            if (extras.containsKey("productSelectedName")) {
                productSelectedName = extras.getString("productSelectedName");
                this.setTitle(productSelectedName);
            }
        }
        detailTitle = (TextView) findViewById(R.id.detailTitle);
        detailImage = (ImageView) findViewById(R.id.detailImage);
        detailText = (TextView) findViewById(R.id.detailText);

        getProductDetail(productSelectedId);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewSaleItem();
                Snackbar.make(view, "Producto agregado al carrito", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void getProductDetail(int productSelectedId) {
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(ProductDetailActivity.this,null,null,0);
        productDetail = db.getProduct(productSelectedId);
        detailTitle.setText(productDetail.getName());
        Log.i("ProductDetailActivity", "productDetail.getImage(): "  + productDetail.getImage());

            File file = this.getFileStreamPath(productDetail.getImage());
            if (file.exists())
            {
                Picasso.get().load(file).into(detailImage);
                Log.i("checkIfImageExist","The image is there >>>" + file.getAbsolutePath());
            }else{
                detailImage.setImageBitmap(null);
                Log.i("checkIfImageExist","The image is not there, >>>" + file.getAbsolutePath());
            }


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            detailText.setText(Html.fromHtml(productDetail.getDetail(),Html.FROM_HTML_MODE_LEGACY));
        } else {
            detailText.setText(Html.fromHtml(productDetail.getDetail()));
        }

        if(productDetail.getId()>0) {
            //Toast.makeText(this, "Subcategorías cargadas", Toast.LENGTH_SHORT).show();
            //textViewWelcome.setText("");
        }else{
            Toast.makeText(this, "No hay productos para esta categoría", Toast.LENGTH_SHORT).show();
        }
    }

    private void addNewSaleItem() {
        int _saleId = Integer.parseInt(saleId);
        int _productId = productDetail.getId();
        String _productName = productDetail.getName();
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(ProductDetailActivity.this,null,null,0);
        db.addNewSaleItem(_saleId,_productId,_productName);
        Toast.makeText(this, "guardado item a la venta n: " + _saleId, Toast.LENGTH_SHORT).show();
    }

}
