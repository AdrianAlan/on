package com.off.on.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.off.on.R;

public class ResultArrayAdapter<T> extends ArrayAdapter<T> {
        private int listItemResId;

        public ResultArrayAdapter(Context context, T[] ts) {
        	super(context, R.layout.fragment_list_item, ts);
        	listItemResId = R.layout.fragment_list_item;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View listItemView = convertView;
            if (null == convertView) { 
                listItemView = inflater.inflate(listItemResId, parent, false);
            }

            TextView lineOneView = (TextView)listItemView.findViewById(
                R.id.textViewMain);
            
            TextView lineTwoView = (TextView)listItemView.findViewById(
                R.id.textViewSub);

            T t = (T)getItem(position); 
            lineOneView.setText("Testing Text - The Title of the item");
            lineTwoView.setText("Testing Text - Added with information, might fill up several lines.");

            return listItemView;
        }
}

