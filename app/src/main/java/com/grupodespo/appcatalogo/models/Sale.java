package com.grupodespo.appcatalogo.models;

import android.content.ContentValues;

public class Sale {
    int id;
    int clientId;
    String clientName;
    String created;

    public Sale(String clientName) {
        this.clientName = clientName;
    }

    public Sale(int clientId, String clientName) {
        this.clientId = clientId;
        this.clientName = clientName;
    }

    public Sale(int id, int clientId, String clientName, String created) {
        this.id = id;
        this.clientId = clientId;
        this.clientName = clientName;
        this.created = created;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    //Metodo para insertar los datos en SQLite
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        if(this.getId()>0){
            values.put("id", this.getId());
        }
        values.put("clientId", this.getClientId());
        values.put("clientName", this.getClientName());
        return values;
    }
}


