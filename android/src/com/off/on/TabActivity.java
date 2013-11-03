package com.off.on;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.Window;
import android.widget.TabWidget;

import com.off.on.fragments.OnListFragment;
import com.off.on.fragments.OnMapFragment;
import com.off.on.models.OnObject;
import com.off.on.utils.Constants;

public class TabActivity extends FragmentActivity {

    private FragmentTabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragmentactivity_tabs);
        TabWidget widget = (TabWidget)findViewById(android.R.id.tabs);
        widget.setStripEnabled(false);
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        
        mTabHost.addTab(mTabHost.newTabSpec(Constants.TAG_MAP).setIndicator(getString(R.string.tab_map_name)),
                OnMapFragment.class, getIntent().getExtras());
        mTabHost.addTab(mTabHost.newTabSpec(Constants.TAG_LIST).setIndicator(getString(R.string.tab_list_name)),
                OnListFragment.class, getIntent().getExtras());

		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString(Constants.STATE_CURRENT_TAB));
		}
	}
	
	public void requestDetailedView(OnObject onObject) {
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra(Constants.onNewActivityOnObject, onObject);
		startActivity(intent);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(Constants.STATE_CURRENT_TAB, (String) mTabHost.getCurrentTabTag());
	}
		
	@Override
	protected void onPause() {
		super.onPause();
		(findViewById(R.id.tabMainView)).setVisibility(View.INVISIBLE);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		(findViewById(R.id.tabMainView)).setVisibility(View.VISIBLE);
	}
}