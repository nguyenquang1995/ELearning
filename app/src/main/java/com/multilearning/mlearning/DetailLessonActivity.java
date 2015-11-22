package com.multilearning.mlearning;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.multilearning.mlearning.adapter.ResultItem;
import com.multilearning.mlearning.model.DetailLesson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by mrken on 11/12/15.
 */
public class DetailLessonActivity extends AppCompatActivity implements View.OnClickListener {

    private String category_id = "";
    private String user_id = "";
    private final String API_BASE = "https://protected-earth-1676.herokuapp.com/";

    private ArrayList<DetailLesson> listDetail;
    private ArrayList<String> listAnswer;
    private ArrayList<ResultItem> listResult;

    private TextView tvNumber;
    private TextView tvQuestion;
    private TextView tvTitleLesson;
    private Button btChosse1;
    private Button btChosse2;
    private Button btChosse3;
    private Button btChosse4;
    private Button btCancel;
    private Button btUpdate;

    private int count = 0;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lesson);

        listDetail = new ArrayList<DetailLesson>();
        listAnswer = new ArrayList<String>();
        listResult = new ArrayList<ResultItem>();

        tvNumber = (TextView) findViewById(R.id.tvNumber);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        tvTitleLesson = (TextView) findViewById(R.id.tvTitle);
        btChosse1 = (Button) findViewById(R.id.btChosse1);
        btChosse2 = (Button) findViewById(R.id.btChosse2);
        btChosse3 = (Button) findViewById(R.id.btChosse3);
        btChosse4 = (Button) findViewById(R.id.btChosse4);
        btCancel = (Button) findViewById(R.id.btCancel);
        btUpdate = (Button) findViewById(R.id.btUpdate);

        Intent intent = getIntent();
        tvTitleLesson.setText(intent.getStringExtra("titleLesson"));
        category_id = intent.getStringExtra("category_id");
        user_id = intent.getStringExtra("user_id");

        new DetailLessonAsyncTask().execute();

        btChosse1.setOnClickListener(this);
        btChosse2.setOnClickListener(this);
        btChosse3.setOnClickListener(this);
        btChosse4.setOnClickListener(this);


        btCancel.setOnClickListener(this);
        btUpdate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btChosse1) {
            count++;
            listAnswer.add(btChosse1.getText().toString());
            CreateLesson();
        }
        if (v.getId() == R.id.btChosse2) {
            count++;
            listAnswer.add(btChosse2.getText().toString());
            CreateLesson();
        }
        if (v.getId() == R.id.btChosse3) {
            count++;
            listAnswer.add(btChosse3.getText().toString());
            CreateLesson();
        }
        if (v.getId() == R.id.btChosse4) {
            count++;
            listAnswer.add(btChosse4.getText().toString());
            CreateLesson();
        }

        if (v.getId() == R.id.btCancel) {
            finish();
        }
        if (v.getId() == R.id.btUpdate) {
            finish();
            Intent intent = new Intent(DetailLessonActivity.this, ResultActivity.class);

            intent.putExtra("result", listResult);
            startActivity(intent);
        }
    }

    private void CreateLesson() {
        if (count < listDetail.size()) {
            tvNumber.setText((count + 1) + "/5");
            tvQuestion.setText(listDetail.get(count).getQuestion());
            btChosse1.setText(listDetail.get(count).getListChosse().get(0));
            btChosse2.setText(listDetail.get(count).getListChosse().get(1));
            btChosse3.setText(listDetail.get(count).getListChosse().get(2));
            btChosse4.setText(listDetail.get(count).getListChosse().get(3));
        } else {
            checkAnswer();

            finish();
            Intent intent = new Intent(DetailLessonActivity.this, ResultActivity.class);

            intent.putExtra("result", listResult);
            startActivity(intent);
        }
    }

    private class DetailLessonAsyncTask extends AsyncTask<Void, Void, Void> {

        private StringBuilder response = new StringBuilder();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(DetailLessonActivity.this);
            progressDialog.setMessage("Parsing ...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {

                URL url = new URL(API_BASE + "users/" + user_id + "/lessons.json");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);

                conn.setConnectTimeout(10000);
                conn.setRequestMethod("POST");
                //set timeout
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject jsonObject = new JSONObject();
                JSONObject jsonUser = new JSONObject();

                jsonObject.put("category_id", category_id);
                jsonObject.put("user_id", user_id);

                jsonUser.put("lesson", jsonObject);

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


                String input = response.substring(response.indexOf("words") + 8, response.length());


                String[] list = input.split("content");
                String question = "";
                String answer = "0";
                ArrayList<String> arr = new ArrayList<String>();

                int count = 0;

                for (int i = 1; i < list.length; i++) {
                    //   Log.d("demo", list[i]);
                    int index1 = list[i].indexOf("category_id");
                    int index2 = list[i].indexOf("correct");
                    int index3 = list[i].indexOf("word_id");

                    if (index1 != -1) {
                        question = list[i].substring(3, index1 - 3);

                    } else if (index2 != -1) {

                        arr.add(list[i].substring(3, index2 - 3));

                        String correct = list[i].substring(index2 + 9, index3 - 2);

                        if (correct.trim().equals("true")) {

                            answer = list[i].substring(3, index2 - 3);
                        }

                    }
                    count++;
                    if (count == 5) {

                        listDetail.add(new DetailLesson(question, arr, answer));

                        question = "";
                        answer = "0";
                        arr.clear();
                        count = 0;

                    }
                }

                conn.disconnect();

            } catch (Exception e) {
                e.printStackTrace();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();

            CreateLesson();

        }
    }

    private void checkAnswer() {

        for (int i = 0; i < listAnswer.size(); i++) {

            ResultItem item = new ResultItem();

            item.setQuestion(listDetail.get(i).getQuestion());
            item.setAnswer(listAnswer.get(i));

            if (listDetail.get(i).getAnswer().equals(listAnswer.get(i))) {
                item.setStatus(true);
            } else {
                item.setStatus(false);
            }

            listResult.add(item);
        }

    }

}

