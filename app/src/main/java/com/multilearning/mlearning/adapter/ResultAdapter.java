package com.multilearning.mlearning.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.multilearning.mlearning.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by mrken on 11/22/15.
 */
public class ResultAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ResultItem> listResult;

    public ResultAdapter(Context context, ArrayList<ResultItem> listResult){
        this.context = context;
        this.listResult = listResult;
    }

    @Override
    public int getCount() {
        return listResult.size();
    }

    @Override
    public Object getItem(int position) {
        return listResult;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.draw_list_result, null);
        }

        ImageView ivResult = (ImageView) convertView.findViewById(R.id.ivResult);
        TextView tvQuestion = (TextView) convertView.findViewById(R.id.tvQuestion);
        TextView tvAnswer = (TextView) convertView.findViewById(R.id.tvAnswer);

        tvQuestion.setText(listResult.get(position).getQuestion());
        tvAnswer.setText(listResult.get(position).getAnswer());

        if(listResult.get(position).getStatus()){
            ivResult.setImageResource(R.drawable.ic_true);
        }else{
            ivResult.setImageResource(R.drawable.ic_false);
        }

        return convertView;
    }
}
