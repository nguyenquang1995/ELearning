package com.multilearning.mlearning.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.multilearning.mlearning.R;
import com.multilearning.mlearning.model.ModelWord;
import java.util.List;

/**
 * Created by hacks_000 on 11/15/2015.
 */
public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {

    private List<ModelWord> listWord;


    public WordListAdapter(List<ModelWord> listWord) {
        this.listWord = listWord;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView wordText;
        public ViewHolder(View itemView) {
            super(itemView);
            CardView cv = (CardView)itemView.findViewById(R.id.placeCard);
            wordText = (TextView) itemView.findViewById(R.id.id_text_word);
        }
    }

    @Override
    public int getItemCount() {
        return listWord.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.wordText.setText(listWord.get(position).getContent());
    }
}
