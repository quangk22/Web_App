package com.examplewebapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listNhanVien;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Ánh xạ
        listNhanVien = (ListView) findViewById(R.id.listViewNhanVien);
        btnAdd = (Button) findViewById(R.id.btnAdd);
//        Thêm
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(intent);
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSON().execute("http://quangtrinh-001-site1.htempurl.com/json.php");

            }
        });

    }

    private String docNoiDung_Tu_URL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // Add username and password for basic authentication
            String username = "11168910";
            String password = "60-dayfreetrial";
            String userCredentials = username + ":" + password;
            String basicAuth = "Basic " + Base64.encodeToString(userCredentials.getBytes(), Base64.NO_WRAP);
            urlConnection.setRequestProperty("Authorization", basicAuth);

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    class docJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            return docNoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            ArrayList<NhanVien> arrNhanVien = new ArrayList<NhanVien>();
            try {
                JSONArray mang = new JSONArray(s);
                for(int i=0;i<mang.length();i++){
                    JSONObject nhanvien = mang.getJSONObject(i);
                    String maNV = String.valueOf(nhanvien.getInt("id"));
                    String tenNV = nhanvien.getString("Tennv");
                    String sdt = nhanvien.getString("Sodt");
                    arrNhanVien.add(new NhanVien(maNV, tenNV, sdt));
                }
                AdapterNhanVien adapter = new AdapterNhanVien(MainActivity.this, arrNhanVien);
                listNhanVien.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this,"Lỗi JSON" + s, Toast.LENGTH_LONG).show();

            }

        }
    }

//

}
