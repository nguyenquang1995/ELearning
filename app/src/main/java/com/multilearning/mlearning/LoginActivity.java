package com.multilearning.mlearning;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mrken on 11/13/15.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private TextView tvRegisterAccount;
    private Button btLogin;
    private EditText etEmail;
    private EditText etPassword;

    private final String API_BASE = "https://protected-earth-1676.herokuapp.com/";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvRegisterAccount = (TextView) findViewById(R.id.tvRegisterAccount);
        btLogin = (Button) findViewById(R.id.btLogin);

        tvRegisterAccount.setOnClickListener(this);
        btLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvRegisterAccount) {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
        if (v.getId() == R.id.btLogin) {
            if (validate()) {
               // startActivity(new Intent(LoginActivity.this, MainActivity.class));
                new LoginAsyncTask().execute();
            }
        }
    }

    private boolean validate() {
        boolean isCheck = true;
        if (etEmail.getText().toString().equals("")) {
            Toast.makeText(this, "Email not null!", Toast.LENGTH_LONG).show();
            isCheck = false;
        } else if (etPassword.getText().toString().equals("")) {
            Toast.makeText(this, "Password not null!", Toast.LENGTH_LONG).show();
            isCheck = false;
        }

        return isCheck;
    }

    public class LoginAsyncTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Checking ...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {

                URL url = new URL(API_BASE + "login.json");

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

                jsonUser.put("session", jsonObject);

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