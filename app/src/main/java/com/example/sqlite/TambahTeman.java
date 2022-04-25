package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TambahTeman extends AppCompatActivity {
    private EditText editNama,editTelpon;
    private Button simpanBtn;
    String nm,tlp;
    int success;

    private static String url_select = "http://10.0.2.2/umyTI/tambahtm.php";
    public static final String TAG = TambahTeman.class.getSimpleName();
    public static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_teman);

        editNama = findViewById(R.id.edNama);
        editTelpon = findViewById(R.id.edTelpon);
        simpanBtn = findViewById(R.id.btnSimpan);

        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpanData();
            }
        });
    }

    public void SimpanData(){
        if (editNama.getText().toString().equals("")||editTelpon.getText().toString().equals("")){
            Toast.makeText(TambahTeman.this,"Semua harus diisi data",Toast.LENGTH_LONG).show();
        }
        else {
            nm = editNama.getText().toString();
            tlp = editTelpon.getText().toString();

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest strReq = new StringRequest(Request.Method.POST, url_select, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Response: " + response.toString());

                    try {
                        JSONObject jObj = new JSONObject(response);
                        success = jObj.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(TambahTeman.this, "Sukses simpan data", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(TambahTeman.this, "gagal", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG,"error : "+error.getMessage());
                    Toast.makeText(TambahTeman.this,"Gagal simpan data",Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String,String> params = new HashMap<>();
                    params.put("nama",nm);
                    params.put("telpon",tlp);

                    return params;
                }
            };
            requestQueue.add(strReq);
        }
    }
}