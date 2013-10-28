package com.off.on;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.Window;

import com.off.on.fragments.OnListFragment;
import com.off.on.fragments.OnMapFragment;

public class TabActivity extends Activity {

	private static final String TAG_MAP = "map";
	private static final String TAG_LIST = "list";
	private static final String STATE_CURRENT_TAB = "state_current_tab";
	private ActionBar bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

		setContentView(R.layout.activity_tab);

		bar = getActionBar();
		bar.setDisplayShowTitleEnabled(false);
		bar.setDisplayShowHomeEnabled(false);
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab mapTab = bar.newTab()
				.setText(R.string.tab_map_name)
				.setTag(TAG_MAP)
				.setTabListener(new TabListener<OnMapFragment>(this, TAG_MAP, OnMapFragment.class));
		Tab listTab = bar.newTab()
				.setText(R.string.tab_list_name)
				.setTag(TAG_LIST)
				.setTabListener(new TabListener<OnListFragment>(this, TAG_LIST, OnListFragment.class));

		bar.addTab(mapTab);
		bar.addTab(listTab);

		if (savedInstanceState != null) {
			if (savedInstanceState.getString(STATE_CURRENT_TAB) == mapTab.getTag()) {
				bar.selectTab(mapTab);
			} else if (savedInstanceState.getString(STATE_CURRENT_TAB) == listTab.getTag()) {
				bar.selectTab(listTab);            
			}
		} 
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(STATE_CURRENT_TAB, (String) bar.getSelectedTab().getTag());
	}

	public static class TabListener<T extends Fragment> implements ActionBar.TabListener {

		private Fragment frag;
		private final Activity act;
		private final String tag;
		private final Class<T> fragClass;
		private FragmentTransaction fragTrans;

		public TabListener(Activity activity, String tag, Class<T> clz) {

			this.act = activity;
			this.tag = tag;
			this.fragClass = clz;

			frag = act.getFragmentManager().findFragmentByTag(tag);
			
			if (frag != null && !frag.isDetached()) {
				act.getFragmentManager().beginTransaction()
				.detach(frag)
				.commit();
			}
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {

			frag = act.getFragmentManager().findFragmentByTag(tag);
			fragTrans = act.getFragmentManager().beginTransaction();

			if (frag == null) {
				frag = Fragment.instantiate(act, fragClass.getName());
				fragTrans.replace(R.id.fragment_container, frag)
				.commit();
			} else {
				fragTrans.attach(frag)
				.commit();
			}
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {

			frag = act.getFragmentManager().findFragmentByTag(tag);
			
			if (frag != null && !frag.isDetached()) {
				act.getFragmentManager()
				.beginTransaction()
				.detach(frag)
				.commit();
			}
		}
	}
}