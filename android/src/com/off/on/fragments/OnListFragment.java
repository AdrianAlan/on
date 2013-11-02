package com.off.on.fragments;

import com.off.on.R;

import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
		getActivity().getActionBar().setSelectedNavigationItem(0);
		// Send id to map.
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
	}
}