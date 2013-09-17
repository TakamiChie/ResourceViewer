package net.onpu_tamago.android.resourceviewer;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LayoutViewerActivity extends ListActivity {

	private static final String TAG = "[LayoutViewer]ResourceViewer";
	private ViewGroup mView;
	private ArrayList<View> mViewList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_viewer);
		Intent intent = getIntent();
		try {
			if (intent != null && intent.hasExtra(MainActivity.EXTRA_LAYOUT)) {
				LayoutInflater inflater = getLayoutInflater();
				FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
				View view = inflater.inflate(
						intent.getIntExtra(MainActivity.EXTRA_LAYOUT, 0), null);
				frame.addView(view);
				mView = view instanceof ViewGroup ? (ViewGroup) view : frame;
				mViewList = new ArrayList<View>();
				ArrayList<String> names = new ArrayList<String>();
				setViewComments(mView, names);
				setListAdapter(new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1, names));
			}
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
			finish();
		}

		// Show the Up button in the action bar.
		setupActionBar();
	}

	private void setViewComments(ViewGroup view, ArrayList<String> names)
			throws IllegalArgumentException, IllegalAccessException {
		int c = view.getChildCount();
		Resources res = getResources();
		Log.d(TAG, "Layout Check Start");
		for (int i = 0; i < c; i++) {
			View v = view.getChildAt(i);
			if (!v.getClass().equals(android.view.View.class)) {
				Log.d(TAG, "index:" + i);
				Log.d(TAG, v.getClass().getName());
				try {
					String n = res.getResourceName(v.getId());
					String shown = res.getResourceEntryName(v.getId());
					names.add(n + ":" + v.getClass().getSimpleName());
					if (v instanceof ImageView) {
						((ImageView) v)
								.setImageResource(android.R.drawable.sym_def_app_icon);
					} else if (v instanceof TextView) {
						((TextView) v).setText(shown);
					} else if (v instanceof ListView) {
						((ListView) v).setAdapter(new ArrayAdapter<String>(
								this, android.R.layout.simple_list_item_1,
								new String[] { shown }));
					}
					v.setTag(n);
					mViewList.add(v);
					if (v instanceof ViewGroup) {
						setViewComments((ViewGroup) v, names);
					}
				} catch (NotFoundException e) {
					// nothing to do
				}
			}
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
