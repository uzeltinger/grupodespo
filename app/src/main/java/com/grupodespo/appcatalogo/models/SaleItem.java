package com.grupodespo.appcatalogo.models;

import android.content.ContentValues;

public class SaleItem {
    int id;
    int saleId;
    int productId;
    String productName;

    public SaleItem(int saleId, int productId, String productName) {
        this.saleId = saleId;
        this.productId = productId;
        this.productName = productName;
    }

    public SaleItem(int id, int saleId, int productId, String productName) {
        this.id = id;
        this.saleId = saleId;
        this.productId = productId;
        this.productName = productName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    //Metodo para insertar los datos en SQLite
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        if(this.getId()>0){
            values.put("id", this.getId());
        }
        values.put("saleId", this.getSaleId());
        values.put("productId", this.getProductId());
        values.put("productName", this.getProductName());
        return values;
    }
}
