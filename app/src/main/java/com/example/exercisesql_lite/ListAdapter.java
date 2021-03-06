package com.example.exercisesql_lite;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
    private Activity context;
    private LayoutInflater inflater;
    private List<varContact> NasItem;

    public ListAdapter(Activity activity, List   <varContact> NasItem) {

        ListAdapter.this.context = activity;
        ListAdapter.this.NasItem = NasItem;
    }

    @Override
    public int getCount() {
        return NasItem.size();
    }

    @Override
    public Object getItem(int location) {
        return NasItem.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) view = inflater.inflate(R.layout.list_kontak, null);

        View rowView= inflater.inflate(R.layout.list_kontak, null, true);

        TextView tvNamaUser = (TextView) rowView.findViewById(R.id.tvNamaUser);
        TextView tvPhone = (TextView) rowView.findViewById(R.id.tvPhone);
        varContact m = NasItem.get(position);

        tvNamaUser.setText(m.getNama());
        tvPhone.setText(m.getPhone());
        return rowView;
    }

}
