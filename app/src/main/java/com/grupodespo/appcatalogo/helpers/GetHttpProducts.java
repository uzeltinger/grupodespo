package com.grupodespo.appcatalogo.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.grupodespo.appcatalogo.ConfigurationActivity;
import com.grupodespo.appcatalogo.MainActivity;
import com.grupodespo.appcatalogo.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GetHttpProducts extends AsyncTask<Void, Void, String> {

    private Context httpContext;
    private List<Product> httpList;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private ArrayList arrayList;
    ProgressDialog progressDialog;
    String urlGeted;

    public GetHttpProducts(Context httpContext, List<Product> httpList, ArrayAdapter<String> adapter, ListView listView, ArrayList arrayList, ProgressDialog progressDialog,String url) {
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
        //String wsURL = "http://grupodespo.com.ar/index.php?option=com_k2&view=products&task=getJson";
        //String wsURL = "https://www.montehermosoalquila.com.ar/tmp/products.json";
        String wsURL = this.urlGeted + "/index.php?option=com_k2&view=products&task=getJson";
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
        JSONArray jsonArray = jsonObject.getJSONArray("products");

        for (int i = 0; i < jsonArray.length(); i++) {

            int id = Integer.parseInt(jsonArray.getJSONObject(i).getString("id"));
            int catid = Integer.parseInt(jsonArray.getJSONObject(i).getString("catid"));
            String name = jsonArray.getJSONObject(i).getString("title");
            String detail = jsonArray.getJSONObject(i).getString("introtext");

            String imageUrl = jsonArray.getJSONObject(i).getString("image");
            String image = "";

            Log.i("onPostExecute","image.length() : " + imageUrl.length());
            if(imageUrl.length()>0){
                String imageName = String.valueOf(id)+".jpg";//"trolltunga.jpg";
                image = imageName;
                Log.d("GetHttpProducts","onPostExecute imageName " + imageName + " sUrl: " + imageUrl);
                new saveImage(imageUrl,imageName);
            }



            this.httpList.add(new Product(id, catid, name, detail, image));
            this.arrayList.add("Producto: " + name);
            this.adapter.notifyDataSetChanged();
        }
        // Crear un nuevo adaptador
        //httpAdapter = new DetailAdapter(this.httpList);
        //httpRecycler.setAdapter(this.httpAdapter);
        } catch (JSONException | UnsupportedEncodingException e) {
        e.printStackTrace();
        }

        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(httpContext,null,null,0);
        //db.emptyProducts();
        db.saveProducts(this.httpList);
        progressDialog.dismiss();
    }


    public static String MD5_Hash(String s) {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(s.getBytes(),0,s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }
}
