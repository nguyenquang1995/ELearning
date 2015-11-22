package com.multilearning.mlearning;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.multilearning.mlearning.util.AppController;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity {

    private String TAG = UpdateProfileActivity.class.getSimpleName();
    ProgressDialog pDialog;
    EditText tvEmail, tvPassword, tvRetypePassword, tvFullname;

    String email, newPass, retypePass, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        tvEmail = (EditText) findViewById(R.id.input_email);
        tvPassword = (EditText) findViewById(R.id.input_password);
        tvRetypePassword = (EditText) findViewById(R.id.input_retype_password);
        tvFullname = (EditText) findViewById(R.id.input_full_name);
    }

    public void onClick(View v) {
        if(v.getId() == R.id.cancel_button) {
            finish();
        }
        if(v.getId() == R.id.update_button) {
            name = tvFullname.getText().toString();
            email = tvEmail.getText().toString();
            newPass = tvPassword.getText().toString();
            retypePass = tvRetypePassword.getText().toString();
            upDate();
        }
    }

    public void upDate() {
        String  tag_string_req3 = "string_req3";

        String url = "https://protected-earth-1676.herokuapp.com/users/";
        String prefname = "info_login";
        // show list word
        SharedPreferences pre = getSharedPreferences(prefname, MODE_PRIVATE);
        int userId = pre.getInt("id", 1);
        url = url + userId + ".json";

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.PUT,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                pDialog.hide();
                Toast.makeText(getApplicationContext(), "Update success", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(UpdateProfileActivity.this, MainActivity.class));

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
                Toast.makeText(getApplicationContext(), "Update fail", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                //params.put("user[email]", email);
                //params.put("user[password]", newPass);
                params.put("user[name]", name);
                //params.put("user[password_confirmation]", retypePass);
                return params;
            }
        };

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req3);
    }

}

