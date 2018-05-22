package com.grupodespo.appcatalogo.models;

import android.content.ContentValues;

public class Venta {
    int id;
    int clientId;
    String clientName;
    int productId;
    String productName;
    String productDetail;
    String productImage;

    public Venta(int id, int clientId, String clientName, int productId, String productName, String productDetail, String productImage) {
        this.id = id;
        this.clientId = clientId;
        this.clientName = clientName;
        this.productId = productId;
        this.productName = productName;
        this.productDetail = productDetail;
        this.productImage = productImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    //Metodo para insertar los datos en SQLite
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put("id", this.getId());
        values.put("clientId", this.getClientId());
        values.put("clientName", this.getClientName());
        values.put("productId", this.getProductId());
        values.put("productName", this.getProductName());
        values.put("productDetail", this.getProductDetail());
        values.put("productImage", this.getProductImage());
        return values;
    }
}

