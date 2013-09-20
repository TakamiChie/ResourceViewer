package net.onpu_tamago.android.resourceviewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class LayoutViewerActivity extends ListActivity {

	private static final String TAG = "[LayoutViewer]ResourceViewer";
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
				int resid = intent.getIntExtra(MainActivity.EXTRA_LAYOUT, 0);
				View view = inflater.inflate(resid, null);
				frame.addView(view);
				setTitle(getResources().getResourceName(resid));
				mViewList = new ArrayList<View>();
				ArrayList<String> names = new ArrayList<String>();
				setViewComments(frame, names);
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
					if (v.getId() != 0xffffffff) {
						String n = res.getResourceName(v.getId());
						String shown = res.getResourceEntryName(v.getId());
						names.add(n + ":" + v.getClass().getSimpleName());
						if (v instanceof ImageView) {
							((ImageView) v)
									.setImageResource(android.R.drawable.sym_def_app_icon);
						} else if (v instanceof TextView) {
							((TextView) v).setText(shown);
						} else if (v instanceof ExpandableListView) {
							List<Map<String, String>> groupList = new ArrayList<Map<String, String>>();
							List<List<Map<String, String>>> childList = new ArrayList<List<Map<String, String>>>();
							Map<String, String> groupElement = new HashMap<String, String>();
							groupElement.put("GROUP_TITLE", "Group");
							groupList.add(groupElement);
							// Childのリスト
							List<Map<String, String>> childElements = new ArrayList<Map<String, String>>();
							for (int j = 0; j < 3; j++) {
								Map<String, String> child = new HashMap<String, String>();
								child.put("CHILD_TITLE", "Item " + j);
								childElements.add(child);
							}
							childList.add(childElements);
							SimpleExpandableListAdapter s = new SimpleExpandableListAdapter(
									this,
									groupList,
									android.R.layout.simple_expandable_list_item_1,
									new String[] { "GROUP_TITLE" },
									new int[] { android.R.id.text1 },
									childList,
									android.R.layout.simple_list_item_1,
									new String[] { "CHILD_TITLE" },
									new int[] { android.R.id.text1 });
							((ExpandableListView) v).setAdapter(s);
						} else if (v instanceof ListView) {
							((ListView) v).setAdapter(new ArrayAdapter<String>(
									this, android.R.layout.simple_list_item_1,
									new String[] { shown }));
						}
						v.setTag(n);
						mViewList.add(v);
					}
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
