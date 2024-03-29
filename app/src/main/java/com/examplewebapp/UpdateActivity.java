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
import android.widget.ImageView;
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

public class UpdateActivity extends AppCompatActivity {
    EditText txtTenNV, txtSDT;
    Button btnChonHinh, btnChupHinh, btnLuu, btnHuy;
    ImageView imgAnhSua;
    String URL= "http://quangtrinh-001-site1.htempurl.com/update.php";
    String  manv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        addConTronl();
        loadData();
        addEvent();

    }

    private void addEvent() {
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNhanVien(URL);
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        Intent intent = getIntent();
        txtTenNV.setText(intent.getStringExtra("NAME"));
        txtSDT.setText(intent.getStringExtra("PHONE"));
         manv = intent.getStringExtra("ID");
    }


    private void addConTronl () {
            txtTenNV = (EditText) findViewById(R.id.editTextNhapTen);
            txtSDT = (EditText) findViewById(R.id.editTextSDT);
//            btnChonHinh = (Button) findViewById(R.id.buttonChonHinh);
//            btnChupHinh = (Button) findViewById(R.id.buttonChupHinh);
            btnLuu = (Button) findViewById(R.id.buttonLuu);
            btnHuy = (Button) findViewById(R.id.buttonQuyLai);
//            imgAnhSua = (ImageView) findViewById(R.id.imageAvatar);

        }

    private void updateNhanVien(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("update data successful")) {
                            Toast.makeText(UpdateActivity.this, "Cập nhật thành công", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(UpdateActivity.this, MainActivity.class));
                        }
                        else
                            Toast.makeText(UpdateActivity.this, "Cập nhật không thành công", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateActivity.this, "Xảy ra lỗi!!!", Toast.LENGTH_LONG).show();
                        Log.d("AAA","Lỗi!\n"+error.toString());
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("MaNV",manv);
                params.put("TenNV",txtTenNV.getText().toString());
                params.put("SDT",txtSDT.getText().toString());
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
