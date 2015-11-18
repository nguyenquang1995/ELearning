package com.multilearning.mlearning;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class testJson extends AppCompatActivity{

    public static final int REQUEST_CODE_LOAD_IMAGE = 114;

    private UserRegisterTask mAuthTask = null;

    ImageView imgvUploadAvatar;
    Button btLeftAction;
    Button btRightAction;

    TextView tvRregisterError;
    EditText edtEmail;
    EditText edtPassword;
    EditText edtRePassword;
    EditText edtFullName;
    Uri filePath = null;

    private View progressView;
    private View registerForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        new UserRegisterTask().execute();

    }


    public class UserRegisterTask extends AsyncTask<Void, Void, Void> {


        private JSONObject result;
      //  private User user;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                String uploadImage = null;
                /*if (mFilePath != null) {
                    Bitmap bitMap = MediaStore.Images.Media.getBitmap(getContentResolver(), mFilePath);
                    uploadImage = getStringImage(bitMap);
                }*/

                URL url = new URL("https://protected-earth-1676.herokuapp.com/login.json");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);

                //set timeout
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject jsonObject = new JSONObject();
                JSONObject jsonUser = new JSONObject();

                jsonObject.put("email", "d@gmail.com");
                jsonObject.put("password", "123456");

                jsonUser.put("session",jsonObject);


//                if (uploadImage != null) {
//                    jsonObject.put("avatar", uploadImage);
//                }
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

                result = new JSONObject(sb.toString());

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
    }
}