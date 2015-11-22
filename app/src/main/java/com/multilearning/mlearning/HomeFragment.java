package com.multilearning.mlearning;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.multilearning.mlearning.adapter.LessonAdapter;
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
public class HomeFragment extends Fragment {

    private final String API_BASE = "https://protected-earth-1676.herokuapp.com/";

    private User user;
    private ArrayList<LessonItem> listLesson;
    private ArrayList<LessonItem> listLessonLearned;

    private ListView lvLesson;
    private LessonAdapter adapterLesson;

    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        listLesson = new ArrayList<LessonItem>();
        listLessonLearned = new ArrayList<LessonItem>();

        lvLesson = (ListView) rootView.findViewById(R.id.lvLesson);

        MainActivity main = (MainActivity) getActivity();
        user = main.getUser();

        new LessonAsyncTask().execute();
        new HomeAsyncTask().execute();



        return rootView;
    }

    public class HomeAsyncTask extends AsyncTask<Void, Void, Void> {

        private String response = "";

        private ArrayList<LessonItem> tmpLesson;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            tmpLesson = new ArrayList<LessonItem>();

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Parsing ...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {

                URL url = new URL(API_BASE + "users/" + user.getUserid() + "/lessons.json");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;

                // System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    response += output;
                }

                getListLessonLearned(response, tmpLesson);

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

            for (int i = 0; i < tmpLesson.size(); i++) {
                LessonItem item = new LessonItem();
                String name = item.search(listLesson, tmpLesson.get(i).getLessonId());
                if (!name.equals("not found")) {
                    item.setLessonId(tmpLesson.get(i).getTitleLesson()); // create_at of lesson
                    item.setTitleLesson(name); // name of lesson
                    listLessonLearned.add(item);
                }
            }

            adapterLesson = new LessonAdapter(getActivity(), listLessonLearned);
            lvLesson.setAdapter(adapterLesson);
        }
    }

    public class LessonAsyncTask extends AsyncTask<Void, Void, Void> {

        private String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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

            //  Toast.makeText(getActivity(), "Size = " + listLesson.get(0).getTitleLesson(), Toast.LENGTH_SHORT).show();
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

            //     Log.d("demo1", r.substring(index1 + 4, index2 - 2) + " : " + r.substring(index2 + 7, index3 - 3));

            listLesson.add(item);
        }


    }

    private void getListLessonLearned(String input, ArrayList<LessonItem> tmp) {

        String result[] = input.split("[}]");
        for (String r : result) {
            LessonItem item = new LessonItem();

            /*
                "id": 4,
                "result": null,
                "user_id": 5,
                "category_id": 1,
                "created_at": "2015-11-19T04:02:17.148Z",
                "updated_at": "2015-11-19T04:02:17.148Z"
            */

            int index1 = r.indexOf("category_id");
            int index2 = r.indexOf("created_at");
            int index3 = r.indexOf("updated_at");

            item.setLessonId(r.substring(index1 + 13, index2 - 2));
            item.setTitleLesson(r.substring(index2 + 13, index3 - 17));

            //  Log.d("demo2", r.substring(index1 + 13, index2 - 2) + " : " + r.substring(index2 + 13, index3 - 17));

            tmp.add(item);
        }
    }

}
