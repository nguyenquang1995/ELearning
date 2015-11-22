package com.multilearning.mlearning;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.multilearning.mlearning.adapter.WordListAdapter;
import com.multilearning.mlearning.model.ModelWord;
import com.multilearning.mlearning.util.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mrken on 11/12/15.
 */
public class WordsFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private int userId;
    private String prefname = "info_login";
    private List<ModelWord> listWords;
    WordListAdapter adapter;
    private JSONArray jsonArray;
    private String TAG = WordsFragment.class.getSimpleName();
    // Tag used to cancel the request
    String tag_json_arry = "json_array_req";
    String url = "https://protected-earth-1676.herokuapp.com/users";
    ProgressDialog pDialog;

    int category_id;
    String learn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_words, container, false);

        Spinner spLevel = (Spinner) rootView.findViewById(R.id.spLevel);
        Spinner spStatus = (Spinner) rootView.findViewById(R.id.spStatus);

        ArrayAdapter<CharSequence> adapterLevel = ArrayAdapter.createFromResource(getActivity(),
                R.array.list_level, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(getActivity(),
                R.array.list_status, android.R.layout.simple_spinner_item);

        adapterLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spLevel.setAdapter(adapterLevel);
        spStatus.setAdapter(adapterStatus);

        // show list word
        SharedPreferences pre = getActivity().getSharedPreferences(prefname, getActivity().MODE_PRIVATE);
        userId = pre.getInt("id", 1);
        url = url + "/" + userId + "/words.json";
        category_id = 1;
        learn = "all";
        loadListWord();
        spLevel.setOnItemSelectedListener(this);
        spStatus.setOnItemSelectedListener(this);
        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() ==  R.id.spLevel) {
            category_id = position + 1;
        }
        else {
            learn = parent.getItemAtPosition(position).toString();
            if(learn.equals("not learned")) {
                learn = "not%20learned";
            }
        }

        updateListWord();
   }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void loadListWord() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        // update url
        url = "https://protected-earth-1676.herokuapp.com/users";
        url = url + "/" + userId + "/words.json";
        url = url + "?category_id=" + category_id + "&learn=" + learn;

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        jsonArray = response;
                        pDialog.hide();
                        getJsonObject();
                        showList();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }

    public void getJsonObject() {
        listWords = new ArrayList<>();
        try {
            if(jsonArray != null) {
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String content = jsonObject.getString("content");
                    int categoryId = jsonObject.getInt("category_id");
                    ModelWord modelWord = new ModelWord(id, content, categoryId);
                    listWords.add(modelWord);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void showList() {
        if(jsonArray != null) {
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            RecyclerView rv = (RecyclerView)getActivity().findViewById(R.id.word_list);
            rv.setLayoutManager(llm);
            adapter = new WordListAdapter(listWords);
            rv.setAdapter(adapter);
        }
    }

    public void updateListWord() {
        String tag_json_arry2 = "json_array_req2";
        pDialog.show();

        // update url
        url = "https://protected-earth-1676.herokuapp.com/users";
        url = url + "/" + userId + "/words.json";
        url = url + "?category_id=" + category_id + "&learn=" + learn;

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        jsonArray = response;
                        pDialog.hide();
                        getJsonObject();
                        showList();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_arry2);
    }

}
