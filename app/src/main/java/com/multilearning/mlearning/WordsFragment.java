package com.multilearning.mlearning;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by mrken on 11/12/15.
 */
public class WordsFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private TextView tvLevelSelection;
    private TextView tvStatusSelection;

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

        spLevel.setOnItemSelectedListener(this);
        spStatus.setOnItemSelectedListener(this);

        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // parent.getItemAtPosition(position).toString();
   }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
