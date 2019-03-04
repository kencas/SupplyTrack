package com.supplytrack;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.supplytrack.adapter.HospitalListArrayAdapter;
import com.supplytrack.model.Hospital;

import java.util.ArrayList;
import java.util.List;

public class HospitalListActivity extends ListActivity {

    public static String RESULT_ID = "id";
    private List<Hospital> hospitalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateHospitalList();

    }

    private void populateHospitalList() {
        ArrayAdapter<Hospital> adapter = new HospitalListArrayAdapter(this, hospitalList);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hospital c = hospitalList.get(position);
                Intent returnIntent = new Intent();
                returnIntent.putExtra(RESULT_ID, c);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }


}