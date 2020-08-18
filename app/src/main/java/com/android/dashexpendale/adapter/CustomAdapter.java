package com.android.dashexpendale.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.android.dashexpendale.R;

public class CustomAdapter extends BaseAdapter {
   private   Context context;
    private   String[] State;
    private   LayoutInflater inflter;

    public CustomAdapter(Context context, String[] state, LayoutInflater inflter) {
        this.context = context;
        State = state;
        this.inflter = inflter;
    }






    @Override
    public int getCount() {
          return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view =inflter.inflate(R.layout.custom_spinner_items,null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(State[i]);
        return view;
    }
}