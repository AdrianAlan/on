package com.off.on;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.off.on.models.OnObject;
import com.off.on.utils.Constants;

public class DetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		OnObject onObject = getIntent().getExtras().getParcelable(
				Constants.onNewActivityOnObject);
		setContentView(R.layout.activity_detail);
		TextView detailedTextView = (TextView) findViewById(R.id.detailTextView);
		detailedTextView.setText(onObject.getInfo());
	}
}
