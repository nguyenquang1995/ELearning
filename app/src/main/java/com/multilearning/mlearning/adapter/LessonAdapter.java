package com.multilearning.mlearning.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multilearning.mlearning.R;
import com.multilearning.mlearning.model.LessonItem;

import java.util.ArrayList;

/**
 * Created by mrken on 11/19/15.
 */
public class LessonAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<LessonItem> listLesson;

    public LessonAdapter(Context context, ArrayList<LessonItem> listLesson){
        this.context = context;
        this.listLesson = listLesson;
    }

    @Override
    public int getCount() {
        return listLesson.size();
    }

    @Override
    public Object getItem(int position) {
        return listLesson.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.draw_list_lesson, null);
        }

        TextView tvTitleLesson = (TextView) convertView.findViewById(R.id.tvTitleLesson);
        TextView tvCreate = (TextView) convertView.findViewById(R.id.tvCreate);
        TextView tvNotify = (TextView) convertView.findViewById(R.id.tvNotify);

        if(listLesson.isEmpty()){
            tvNotify.setText("Bạn chưa học tiết học nào cả !");
        }else {

            tvTitleLesson.setText(listLesson.get(position).getTitleLesson().toString());
            if (listLesson.get(position).getLessonId().indexOf("-") != -1) {
                tvCreate.setText(listLesson.get(position).getLessonId().toString());
            } else {
                tvCreate.setText("");
            }
        }

        return convertView;
    }
}
