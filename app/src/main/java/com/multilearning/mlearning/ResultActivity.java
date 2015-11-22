package com.multilearning.mlearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.multilearning.mlearning.adapter.ResultAdapter;
import com.multilearning.mlearning.adapter.ResultItem;

import java.util.ArrayList;

/**
 * Created by mrken on 11/21/15.
 */
public class ResultActivity extends AppCompatActivity {

    private ArrayList<ResultItem> listResult;

    private TextView tvResult;
    private ListView lvResult;
    private ResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResult = (TextView) findViewById(R.id.tvResult);
        lvResult = (ListView) findViewById(R.id.lvResult);

        listResult = (ArrayList<ResultItem>) getIntent().getSerializableExtra("result");

        tvResult.setText("Result: " + getCountAnswer() + "/" + listResult.size());

        adapter = new ResultAdapter(ResultActivity.this, listResult);
        lvResult.setAdapter(adapter);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }

    private int getCountAnswer() {
        int count = 0;
        for (int i = 0; i < listResult.size(); i++)
            if (listResult.get(i).getStatus())
                count++;
        return count;
    }

}
