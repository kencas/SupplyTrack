package com.supplytrack.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.supplytrack.model.Hospital;

import com.supplytrack.R;

import java.util.List;

public class HospitalListArrayAdapter extends ArrayAdapter<Hospital> {

    private final List<Hospital> list;
    private final Activity context;

    static class ViewHolder {
        protected TextView name;
    }

    public HospitalListArrayAdapter(Activity context, List<Hospital> list) {
        super(context, R.layout.activity_countrycode_row, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_countrycode_row, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(list.get(position).getHospital_name());
        return view;
    }
}