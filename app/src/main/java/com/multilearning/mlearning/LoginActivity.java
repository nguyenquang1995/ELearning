package com.multilearning.mlearning;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.multilearning.mlearning.model.User;

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

    private String email = "";
    private String password = "";
    private String name = "";
    private String userid = "";

    private User user;

    private String prefname = "info_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvRegisterAccount = (TextView) findViewById(R.id.tvRegisterAccount);
        btLogin = (Button) findViewById(R.id.btLogin);

        user = new User();

        tvRegisterAccount.setOnClickListener(this);
        btLogin.setOnClickListener(this);


    }

    @Override
    protected void onPause() {
        super.onPause();
        savingPreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoringPreferences();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvRegisterAccount) {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
        if (v.getId() == R.id.btLogin) {
            if (validate()) {
                // startActivity(new Intent(LoginActivity.this, MainActivity.class));
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                new LoginAsyncTask().execute();

            }
        }
    }

    private boolean validate() {
        boolean isCheck = true;

        if (etEmail.getText().toString().equals("")) {
            Toast.makeText(this, "Email not null!", Toast.LENGTH_SHORT).show();
            isCheck = false;
        } else {
            if (etEmail.getText().toString().indexOf('@') != -1 && etEmail.getText().toString().indexOf(".com") != -1
                    && etEmail.getText().toString().indexOf("@.com") == -1) {
                isCheck = true;
            } else {
                Toast.makeText(this, "Email wrong format!", Toast.LENGTH_SHORT).show();
                isCheck = false;
            }
        }

        if (isCheck) {
            if (etPassword.getText().toString().equals("")) {
                Toast.makeText(this, "Password not null!", Toast.LENGTH_SHORT).show();
                isCheck = false;
            } else if (etPassword.getText().toString().length() < 6) {
                Toast.makeText(this, "Password is too short (minimum is 6 characters)", Toast.LENGTH_SHORT).show();
                isCheck = false;
            }
        }

        return isCheck;
    }

    public class LoginAsyncTask extends AsyncTask<Void, Void, Void> {

        private StringBuilder response = new StringBuilder();

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

                jsonObject.put("email", email);
                jsonObject.put("password", password);

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

                // System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    response.append(output + "/n");
                }

                /*
                    "id": 1
                */
                int index1 = response.indexOf("id");
                int index2 = response.indexOf("name");

                user.setUserid(response.substring(index1 + 4, index2 - 2));
             //   Log.d("demo", userid);
                /*
                    "name": "Duong Ngo",
                    "email": "d@gmail.com"
                */

                index2 += 7;
                int index3 = response.indexOf("email");
                index3 -= 3;

                user.setName(response.substring(index2, index3));
             //   Log.d("demo", name);

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
            if (isCheckServer(response)) {
               // startActivity(new Intent(LoginActivity.this, MainActivity.class));
                user.setEmail(email);


                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("info_user", user);
                intent.putExtra("user", bundle);
                /*intent.putExtra("email", email);
                intent.putExtra("name", name);
                intent.putExtra("userid", userid);*/
                startActivity(intent);

            } else {
                Toast.makeText(LoginActivity.this, "Email or Password incorrect!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isCheckServer(StringBuilder respond) {
        if (respond.indexOf(email) != -1)
            return true;
        else
            return false;
    }

    /**
     * hàm lưu trạng thái
     */
    public void savingPreferences() {
        //tạo đối tượng getSharedPreferences
        SharedPreferences pre = getSharedPreferences
                (prefname, MODE_PRIVATE);
        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor = pre.edit();

        editor.putString("email", email);
        editor.putString("password", password);
        // TODO // FIXME: 11/21/15 
        editor.putInt("id", 8);

        //chấp nhận lưu xuống file
        editor.commit();
    }

    /**
     * hàm đọc trạng thái đã lưu trước đó
     */
    public void restoringPreferences() {
        SharedPreferences pre = getSharedPreferences
                (prefname, MODE_PRIVATE);
        //lấy user, pwd, nếu không thấy giá trị mặc định là rỗng
        String user = pre.getString("email", "");
        String pwd = pre.getString("password", "");
    }

}