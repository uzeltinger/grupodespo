package com.grupodespo.appcatalogo.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.grupodespo.appcatalogo.models.Category;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;



public class GetHttpCategories extends AsyncTask<Void, Void, String> {

    private Context httpContext;
    private List<Category> httpList;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private ArrayList arrayList;
    ProgressDialog progressDialog;
    String urlGeted;

    public GetHttpCategories(Context httpContext, List<Category> httpList, ArrayAdapter<String> adapter, ListView listView, ArrayList arrayList, ProgressDialog progressDialog,String url) {
        this.httpContext = httpContext;
        this.httpList = httpList;
        this.adapter = adapter;
        this.listView = listView;
        this.arrayList = arrayList;
        this.progressDialog = progressDialog;
        this.urlGeted = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(httpContext, "Descargando", "por favor, espere");
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result = null;
        try {
            //String wsURL = "https://www.montehermosoalquila.com.ar/tmp/categorias.json";
            String wsURL = this.urlGeted + "/index.php?option=com_k2&view=categories&task=getJson";
            Log.d("wsURL" ,wsURL);
            URL url = new URL(wsURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            result = inputStreamToString(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader rd = new BufferedReader(isr);
        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(URLDecoder.decode(s, "UTF-8" ));
            JSONArray jsonArray = jsonObject.getJSONArray("categorias");
            for (int i = 0; i < jsonArray.length(); i++) {
                int id = Integer.parseInt(jsonArray.getJSONObject(i).getString("id"));
                int parent = Integer.parseInt(jsonArray.getJSONObject(i).getString("parent"));
                String name = jsonArray.getJSONObject(i).getString("name");

                this.httpList.add(new Category(id, parent, name));
                String msj = "id: " + id + " parent: " + parent + " name: " + name ;
                Log.d("onPostExecute" ,msj);
                this.arrayList.add("Categoria: " + name);
                this.adapter.notifyDataSetChanged();
            }
            // Crear un nuevo adaptador
            //httpAdapter = new DetailAdapter(this.httpList);
            //httpRecycler.setAdapter(this.httpAdapter);
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(httpContext,null,null,0);
        //db.emptyCategories();
        db.saveCategories(this.httpList);
        progressDialog.dismiss();
    }
}
