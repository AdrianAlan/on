package com.off.on.fragments;

import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class OnListFragment extends ListFragment {

	private static String[] test = {"test1", "test2", "test3"};
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(new ResultArrayAdapter<String>(getActivity(), test));

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
	}
}