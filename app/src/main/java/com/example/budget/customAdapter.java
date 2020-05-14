package com.example.budget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class customAdapter extends BaseAdapter {

    Context context;
    ArrayList<objects> arrayList;

    public customAdapter(Context context, ArrayList<objects> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.customlistview, null);

        TextView t1 = (TextView) convertView.findViewById(R.id.reason);
        TextView t2 = (TextView) convertView.findViewById(R.id.money);

        objects object = arrayList.get(position);

        t1.setText(object.getName());
        t2.setText(String.valueOf(object.getValue()));


        return convertView;
    }
}
