package com.examplewebapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class InsertActivity extends AppCompatActivity {
    EditText editTextID,editTextName,editTextSDT;
    Button btnThem,btnHuy;
    String URL= "http://quangtrinh-001-site1.htempurl.com/insert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
//        Ánh Xạ
        editTextID = (EditText) findViewById(R.id.editTextId);
        editTextName = (EditText) findViewById(R.id.editTextTen);
        editTextSDT = (EditText) findViewById(R.id.editTextSdt);

        btnThem = (Button) findViewById(R.id.btnThem);
        btnHuy = (Button) findViewById(R.id.btnHuyBo);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themNhanVien(URL);
            }
        });
    }
    private void themNhanVien(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("insert data successful")) {
                            Toast.makeText(InsertActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(InsertActivity.this, MainActivity.class));
                        }
                        else
                            Toast.makeText(InsertActivity.this, "Thêm không thành công", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InsertActivity.this, "Xảy ra lỗi!!!", Toast.LENGTH_LONG).show();
                        Log.d("AAA","Lỗi!\n"+error.toString());
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("MaNV",editTextID.getText().toString());
                params.put("TenNV",editTextName.getText().toString());
                params.put("SDT",editTextSDT.getText().toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentials = "11168910:60-dayfreetrial"; // username:password
                String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }
}