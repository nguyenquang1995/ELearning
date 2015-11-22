package com.multilearning.mlearning;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.multilearning.mlearning.util.AppController;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mrken on 11/14/15.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    private String TAG = RegisterActivity.class.getSimpleName();
    ProgressDialog pDialog;

    private EditText etUser;
    private EditText etPassword;
    private EditText etPasswordConfirmation;
    private EditText etEmail;

    private String name, email, password, passConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUser = (EditText)findViewById(R.id.input_full_name);
        etPassword = (EditText)findViewById(R.id.input_password);
        etPasswordConfirmation = (EditText)findViewById(R.id.input_retype_password);
        etEmail = (EditText)findViewById(R.id.input_email);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.update_button) {
            name = etUser.getText().toString();
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();
            passConfirm = etPasswordConfirmation.getText().toString();
            signUp();
        }
        if(v.getId() == R.id.cancel_button) {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        }

        if(v.getId() == R.id.avatar_default) {
           /* loadImagefromGallery(v);*/
        }
    }

    public void signUp() {


        String  tag_string_req = "string_req";

        String url = "https://protected-earth-1676.herokuapp.com/users.json";

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                pDialog.hide();
                Toast.makeText(getApplicationContext(), "Register success", Toast.LENGTH_LONG).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
                Toast.makeText(getApplicationContext(), "Email has already been taken or password confirmation doesn't match password ", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user[email]", email);
                params.put("user[password]", password);
                params.put("user[name]", name);
                params.put("user[password_confirmation]", passConfirm);
                return params;
            }
        };

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /*public void loadImagefromGallery(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // when an Image is picked
            if(requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                // get the image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                // move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imageView = (ImageView) findViewById(R.id.avatar_default);
                // Set the Image in ImageView after decoding the String
                imageView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }*/
}
