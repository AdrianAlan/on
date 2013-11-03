package com.off.on.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.off.on.R;
import com.off.on.TabActivity;
import com.off.on.models.OnObject;
import com.off.on.utils.Constants;

public class OnListFragment extends ListFragment {

	private ResultArrayAdapter<OnObject> arrayAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayList<Parcelable> tempList = getArguments().getParcelableArrayList(
				Constants.onNewActivityDetails);
		arrayAdapter = new ResultArrayAdapter<OnObject>(getActivity(),
				(OnObject[]) tempList.toArray(new OnObject[tempList.size()]));
		setListAdapter(arrayAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		((TabActivity) getActivity()).requestDetailedView(arrayAdapter
				.getItem(position));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_list, container, false);
	}
}