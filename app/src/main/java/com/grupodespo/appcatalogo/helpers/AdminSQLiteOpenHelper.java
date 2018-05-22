package com.grupodespo.appcatalogo.helpers;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.grupodespo.appcatalogo.models.Category;
import com.grupodespo.appcatalogo.models.Product;
import com.grupodespo.appcatalogo.models.Sale;
import com.grupodespo.appcatalogo.models.SaleItem;
import com.grupodespo.appcatalogo.models.Venta;

import java.util.ArrayList;
import java.util.List;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "grupodesco.db";
    private List<Sale> sale = new ArrayList();
    private SaleItem sale_item;

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists categorias(" +
                "id integer primary key," +
                "parent integer," +
                "name text" +
                ")");
        db.execSQL("create table if not exists productos(" +
                "id integer primary key," +
                "catid integer," +
                "name text," +
                "detail text," +
                "image text" +
                ")");
        db.execSQL("create table if not exists ventas(" +
                "id integer primary key autoincrement," +
                "clientId integer," +
                "clientName text," +
                "created timestamp not null default current_timestamp" +
                ")");
        db.execSQL("create table if not exists venta_item(" +
                "id integer primary key autoincrement," +
                "saleId integer," +
                "productId integer," +
                "productName text" +
                ")");
        Log.d("AdminSQLiteOpenHelper", "onCreate");
    }

    public void doLoadInitialData(){
        SQLiteDatabase db = getReadableDatabase();
        sale.add(new Sale(1,1,"fabio",""));
        for(int i=0; i<sale.size(); i++) {
            long insert =  db.insert("ventas",
                    null, sale.get(i).toContentValues());
            Log.d("AdminSQLiteOpenHelper" ,"doLoadInitialData ventas agrtegadas: "
                    + sale.size());
        }
    }

    public long addNewSale(String cliente){
        SQLiteDatabase db = getReadableDatabase();
        sale.add(new Sale(2,cliente));
        long insert =  db.insert("ventas",
                null, sale.get(0).toContentValues());
        Log.d("AdminSQLiteOpenHelper" ,"addNewSale venta agrtegada: "
                + insert);
        return insert;
    }

    public void addNewSaleItem(int _saleId, int _productId, String _productName){
        SQLiteDatabase db = getReadableDatabase();
        sale_item = new SaleItem(_saleId,_productId,_productName);
        long insert =  db.insert("venta_item",
                null, sale_item.toContentValues());
        Log.d("AdminSQLiteOpenHelper" ,"addNewSaleItem item: " + _productId + " de venta: " + _saleId + " agregado: "
                + _productName);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS categorias");
        db.execSQL("DROP TABLE IF EXISTS productos");
        db.execSQL("DROP TABLE IF EXISTS ventas");
        db.execSQL("DROP TABLE IF EXISTS venta_item");
        onCreate(db);
    }

    public void restartDataBase() {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS categorias");
        db.execSQL("DROP TABLE IF EXISTS productos");
        db.execSQL("DROP TABLE IF EXISTS ventas");
        db.execSQL("DROP TABLE IF EXISTS venta_item");
        onCreate(db);
    }

    public void saveCategories(List<Category> cats){
        emptyCategories();
        SQLiteDatabase db = getReadableDatabase();
        for(int i=0; i<cats.size(); i++) {
            long insert =  db.insert("categorias",
                    null, cats.get(i).toContentValues());
        }
        Log.d("AdminSQLiteOpenHelper" ,"saveCategories categorias agrtegadas: "
                + cats.size());
    }
    public void saveProducts(List<Product> products){
        SQLiteDatabase db = getReadableDatabase();
        emptyProducts();
        for(int i=0; i<products.size(); i++) {
            long insert =  db.insert("productos",
                    null, products.get(i).toContentValues());
        }
        Log.d("AdminSQLiteOpenHelper" ,"saveProducts productos agrtegados: "
                + products.size());
    }
    public void emptyCategories(){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS categorias");
        Log.d("AdminSQLiteOpenHelper", "emptyCategories");
        onCreate(db);
    }
    public void emptyProducts(){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS productos");
        Log.d("AdminSQLiteOpenHelper", "emptyProductos");
        onCreate(db);
    }
    public List<Category> getAllCategories(){
        List<Category> list = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        //doLoadInitialData(db);
        Cursor c = db.query(
                "categorias",  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                null,  // Columnas para la cláusula WHERE
                null,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                "name"  // Cláusula ORDER BY
        );
        while(c.moveToNext()){
            int id = Integer.parseInt(c.getString(c.getColumnIndex("id")));
            int parent = Integer.parseInt(c.getString(c.getColumnIndex("parent")));
            String name = c.getString(c.getColumnIndex("name"));
            Category item = new Category(id, parent, name);
            list.add(item);
        }
        Log.d("AdminSQLiteOpenHelper" ,"getAllCategories total: " + list.size());
        return list;
    }
    public List<Category> getCategories(int categoryParentId){
        List<Category> list = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        //doLoadInitialData(db);
        Cursor c = db.query(
                "categorias",  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                "parent=?",  // Columnas para la cláusula WHERE
                new String[] { String.valueOf(categoryParentId)},  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                "name"  // Cláusula ORDER BY
        );
        while(c.moveToNext()){
            int id = Integer.parseInt(c.getString(c.getColumnIndex("id")));
            int parent = Integer.parseInt(c.getString(c.getColumnIndex("parent")));
            String name = c.getString(c.getColumnIndex("name"));
            Category item = new Category(id, parent, name);
            list.add(item);
        }
        Log.d("AdminSQLiteOpenHelper" ,"getCategories total: " + list.size());
        return list;
    }

    public List<Product> getProducts(int categoryParentId){
        List<Product> list = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        //doLoadInitialData(db);
        Cursor c = db.query(
                "productos",  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                "catid=?",  // Columnas para la cláusula WHERE
                new String[] { String.valueOf(categoryParentId)},  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                "name"  // Cláusula ORDER BY
        );
        while(c.moveToNext()){
            int id = Integer.parseInt(c.getString(c.getColumnIndex("id")));
            int catid = Integer.parseInt(c.getString(c.getColumnIndex("catid")));
            String name = c.getString(c.getColumnIndex("name"));
            String detail = c.getString(c.getColumnIndex("detail"));
            String image = c.getString(c.getColumnIndex("image"));
            Product item = new Product(id, catid, name, detail, image);
            list.add(item);
        }
        Log.d("AdminSQLiteOpenHelper" ,"getProducts total: " + list.size());
        return list;
    }

    public Product getProduct(int productSelectedId){
        Product product;
        Product item = new Product();
        SQLiteDatabase db = getReadableDatabase();
        //doLoadInitialData(db);
        Cursor c = db.query(
                "productos",  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                "id=?",  // Columnas para la cláusula WHERE
                new String[] { String.valueOf(productSelectedId)},  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                "name"  // Cláusula ORDER BY
        );
        while(c.moveToNext()){
            int id = Integer.parseInt(c.getString(c.getColumnIndex("id")));
            int catid = Integer.parseInt(c.getString(c.getColumnIndex("catid")));
            String name = c.getString(c.getColumnIndex("name"));
            String detail = c.getString(c.getColumnIndex("detail"));
            String image = c.getString(c.getColumnIndex("image"));
            item = new Product(id, catid, name, detail, image);
            return item;
        }
        Log.d("AdminSQLiteOpenHelper" ,"getProducts total: " + item);
        return null;
    }

    public List<Sale> getSales(){
        List<Sale> list = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        //doLoadInitialData(db);
        Cursor c = db.query(
                "ventas",  // Nombre de la tabla
                new String[] {"id", "clientId", "clientName", "strftime('%d-%m-%Y',created) AS created"},
                //"id,clientId,clientName,strftime('%d-%m-%Y',created)",  // Lista de Columnas a consultar
                null,  // Columnas para la cláusula WHERE
                null,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                "id DESC"  // Cláusula ORDER BY
        );
        while(c.moveToNext()){
            int id = Integer.parseInt(c.getString(c.getColumnIndex("id")));
            int clientId = Integer.parseInt(c.getString(c.getColumnIndex("clientId")));
            String clientName = c.getString(c.getColumnIndex("clientName"));
            String created = c.getString(c.getColumnIndex("created"));
            Sale item = new Sale(id, clientId, clientName, created);
            list.add(item);
            Log.d("AdminSQLiteOpenHelper" ,"getSales item: " + id + " created: " + created);
        }
        Log.d("AdminSQLiteOpenHelper" ,"getSales total: " + list.size());
        return list;
    }


    public List<SaleItem> getSaleItems(int saleId){
        Log.d("AdminSQLiteOpenHelper" ,"getSaleItems saleId: " + saleId);
        List<SaleItem> list = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        //doLoadInitialData(db);
        Cursor c = db.query(
                "venta_item",  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                "saleId=?",  // Columnas para la cláusula WHERE
                new String[] { String.valueOf(saleId)},  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                "productName ASC"  // Cláusula ORDER BY
        );
        while(c.moveToNext()){
            int id = Integer.parseInt(c.getString(c.getColumnIndex("id")));
            int productId = Integer.parseInt(c.getString(c.getColumnIndex("productId")));
            String productName = c.getString(c.getColumnIndex("productName"));
            SaleItem item = new SaleItem(id, productId, productName);//, detail, image
            list.add(item);
        }
        Log.d("AdminSQLiteOpenHelper" ,"getSaleItems total: " + list.size());
        return list;
    }
    public void deleteSales(){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DELETE FROM venta_item");
        db.execSQL("DELETE FROM ventas");
        Log.d("AdminSQLiteOpenHelper", "venta_item y ventas");
        onCreate(db);
    }

}
