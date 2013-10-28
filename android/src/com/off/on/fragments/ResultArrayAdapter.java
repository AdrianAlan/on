package com.off.on.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class ResultArrayAdapter<T> extends ArrayAdapter<T> {
        private int mListItemLayoutResId;

        public ResultArrayAdapter(Context context, T[] ts) {
        	super(context, android.R.layout.two_line_list_item, ts);
        	mListItemLayoutResId = android.R.layout.two_line_list_item;
        }

        @Override
        public android.view.View getView(
                int position, 
                View convertView,
                ViewGroup parent) {


            LayoutInflater inflater = (LayoutInflater)getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View listItemView = convertView;
            if (null == convertView) { 
                listItemView = inflater.inflate(
                    mListItemLayoutResId, 
                    parent, 
                    false);
            }

            // The ListItemLayout must use the standard text item IDs.
            TextView lineOneView = (TextView)listItemView.findViewById(
                android.R.id.text1);
            
            TextView lineTwoView = (TextView)listItemView.findViewById(
                android.R.id.text2);

            T t = (T)getItem(position); 
            lineOneView.setText("Test1");
            lineTwoView.setText("Test2");

            return listItemView;
        }
}

