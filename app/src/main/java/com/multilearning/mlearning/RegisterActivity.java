package com.multilearning.mlearning;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mrken on 11/14/15.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private final String API_BASE = "https://protected-earth-1676.herokuapp.com/";
    private ProgressDialog progressDialog;

    private EditText etUser;
    private EditText etPassword;
    private EditText etPasswordConfirmation;
    private EditText etEmail;


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

    }

    public class LoginAsyncTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Checking ...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {

                URL url = new URL(API_BASE + "users.json");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);

                //set timeout
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject jsonObject = new JSONObject();
                JSONObject jsonUser = new JSONObject();

                jsonObject.put("email", "d@gmail.com");
                jsonObject.put("password", "1234567");
                jsonObject.put("password_confirmation","1234567");
                jsonObject.put("name", "nguyen van minh");
                jsonUser.put("user", jsonObject);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(jsonUser.toString());
                writer.flush();
                writer.close();


                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;
                StringBuilder sb = new StringBuilder();

                // System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    sb.append(output + "/n");
                }
                Log.d("demo", "Hello" + sb.toString());

                //  result = new JSONObject(sb.toString());

                conn.disconnect();

            } catch (Exception e) {
                e.printStackTrace();

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }
    }
}
