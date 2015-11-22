package com.multilearning.mlearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.multilearning.mlearning.adapter.ResultAdapter;
import com.multilearning.mlearning.adapter.ResultItem;

import java.util.ArrayList;

/**
 * Created by mrken on 11/21/15.
 */
public class ResultActivity extends AppCompatActivity {

    private ArrayList<ResultItem> listResult;
    private ListView lvResult;
    private ResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        lvResult = (ListView) findViewById(R.id.lvResult);

        listResult = (ArrayList<ResultItem>) getIntent().getSerializableExtra("result");

        adapter = new ResultAdapter(ResultActivity.this, listResult);
        lvResult.setAdapter(adapter);

    }


}
