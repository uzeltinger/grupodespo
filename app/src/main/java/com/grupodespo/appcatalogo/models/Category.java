package com.grupodespo.appcatalogo.models;
import android.content.ContentValues;

public class Category {
    int id;
    int parent;
    String name;
    public Category() {
    }
    public Category(int parent, String name) {
        this.parent = parent;
        this.name = name;
    }
    public Category(String name) {
        this.name = name;
    }
    public Category(int id, int parent, String name) {
        this.id = id;
        this.parent = parent;
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getParent() {
        return parent;
    }
    public void setParent(int parent) {
        this.parent = parent;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    //Metodo para insertar los datos en SQLite
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        // Pares clave-valor
        values.put("id", this.getId());
        values.put("parent", this.getParent());
        values.put("name", this.getName());
        return values;
    }
}
