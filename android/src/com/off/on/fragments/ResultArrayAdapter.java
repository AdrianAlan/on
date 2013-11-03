package com.off.on.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.off.on.R;
import com.off.on.models.OnObject;

public class ResultArrayAdapter<T> extends ArrayAdapter<OnObject> {
	private int listItemResId;

	public ResultArrayAdapter(Context context, OnObject[] objects) {
		super(context, R.layout.fragment_list_item, objects);
		listItemResId = R.layout.fragment_list_item;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View listItemView = convertView;
		if (null == convertView) {
			listItemView = inflater.inflate(listItemResId, parent, false);
		}

		TextView lineOneView = (TextView) listItemView
				.findViewById(R.id.textViewMain);
		TextView lineTwoView = (TextView) listItemView
				.findViewById(R.id.textViewSub);

		OnObject onObject = (OnObject) getItem(position);
		lineOneView.setText(onObject.getName());
		lineTwoView.setText(onObject.getCategory() + ": " + onObject.getInfo());

		return listItemView;
	}
}
