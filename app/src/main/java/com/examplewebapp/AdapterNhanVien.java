package com.examplewebapp;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterNhanVien extends BaseAdapter {
    private Context context;
    private ArrayList<NhanVien> listNhanVien;

    public AdapterNhanVien(Context context, ArrayList<NhanVien> listNhanVien) {
        this.context = context;
        this.listNhanVien = listNhanVien;
    }
    @Override
    public int getCount() {
        return listNhanVien.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        }
        TextView txtMaNV = (TextView) convertView.findViewById(R.id.textMaNV);
        TextView txtTenNV = (TextView) convertView.findViewById(R.id.textTenNV);
        TextView txtSDT = (TextView) convertView.findViewById(R.id.textSDT);

        Button btnSua = (Button) convertView.findViewById(R.id.buttonSua);
        Button btnXoa = (Button) convertView.findViewById(R.id.buttonXoa);

        NhanVien nhanVien = listNhanVien.get(position);
        txtMaNV.setText(nhanVien.MaNV);
        txtTenNV.setText(nhanVien.TenNV);
        txtSDT.setText(nhanVien.SDT);


//
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context ,UpdateActivity.class);
                intent.putExtra("ID",nhanVien.MaNV);
                intent.putExtra("NAME",nhanVien.TenNV);
                intent.putExtra("PHONE",nhanVien.SDT);
                context.startActivity(intent);
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL = "http://quangtrinh-001-site1.htempurl.com/delete.php?MaNV=" + nhanVien.MaNV;
                String id = nhanVien.MaNV;
                xoaNhanVien(URL,id);
            }
        });
//
        return convertView;
    }
    private void xoaNhanVien(String url, String id) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, // Sử dụng phương thức POST
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("Delete data successful")) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_LONG).show();
                            // Refresh lại activity hoặc load lại dữ liệu
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                        } else
                            Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Xảy ra lỗi!!!", Toast.LENGTH_LONG).show();
                        Log.d("AAA", "Lỗi!\n" + error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Truyền tham số MaNV dưới dạng POST
                Map<String, String> params = new HashMap<>();
                params.put("MaNV", id);
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
