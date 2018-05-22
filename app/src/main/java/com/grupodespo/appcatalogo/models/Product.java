package com.grupodespo.appcatalogo.models;
import android.content.ContentValues;

public class Product {
    int id;
    int catid;
    String name;
    String detail;
    String image;

    public Product(){

    }

    public Product(int id, int catid, String name, String detail, String image) {
        this.id = id;
        this.catid = catid;
        this.name = name;
        this.detail = detail;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {        return detail;    }

    public void setDetail(String detalle) {        this.detail = detalle;    }

    public String getImage() {        return image;    }

    public void setImage(String image) {        this.image = image;    }

    //Metodo para insertar los datos en SQLite
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        // Pares clave-valor
        values.put("id", this.getId());
        values.put("catid", this.getCatid());
        values.put("name", this.getName());
        values.put("detail", this.getDetail());
        values.put("image", this.getImage());
        return values;
    }
}
