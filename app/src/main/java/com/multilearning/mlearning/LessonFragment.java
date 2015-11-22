package com.multilearning.mlearning;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.multilearning.mlearning.adapter.LessonAdapter;
import com.multilearning.mlearning.model.DetailLesson;
import com.multilearning.mlearning.model.LessonItem;
import com.multilearning.mlearning.model.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by mrken on 11/12/15.
 */
public class LessonFragment extends Fragment {

    private User user;

    private final String API_BASE = "https://protected-earth-1676.herokuapp.com/";
    private ProgressDialog progressDialog;

    private ArrayList<LessonItem> listLesson;
    private ListView lvLesson;
    private LessonAdapter adapterLesson;
    private ArrayList<DetailLesson> listDetail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lesson, container, false);

        listLesson = new ArrayList<LessonItem>();
        listDetail = new ArrayList<DetailLesson>();

        lvLesson = (ListView) rootView.findViewById(R.id.lvLesson);

        MainActivity main = (MainActivity) getActivity();
        user = main.getUser();

        new LessonAsyncTask().execute();

        lvLesson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), DetailLessonActivity.class);
                intent.putExtra("category_id", listLesson.get(position).getLessonId());
                intent.putExtra("user_id", user.getUserid());
                intent.putExtra("titleLesson", listLesson.get(position).getTitleLesson());
                startActivity(intent);
            }
        });

        return rootView;
    }



    public class LessonAsyncTask extends AsyncTask<Void, Void, Void> {

        private String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Parsing ...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {

                URL url = new URL(API_BASE + "categories.json");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;

                // System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    response += output;
                }

                getListLesson(response);

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

            adapterLesson = new LessonAdapter(getActivity(), listLesson);
            lvLesson.setAdapter(adapterLesson);
        }
    }

    private void getListLesson(String input) {

        String result[] = input.split("[}]");
        for (String r : result) {
            LessonItem item = new LessonItem();

            int index1 = r.indexOf("id");
            int index2 = r.indexOf("name");
            int index3 = r.indexOf("created_at");

            item.setLessonId(r.substring(index1 + 4, index2 - 2));
            item.setTitleLesson(r.substring(index2 + 7, index3 - 3));

            listLesson.add(item);
        }
    }



}
