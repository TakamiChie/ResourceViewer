package net.onpu_tamago.android.resourceviewer;

import net.onpu_tamago.android.resourceviewer.viewer.AnimationFragment;
import net.onpu_tamago.android.resourceviewer.viewer.AnimatorFragment;
import net.onpu_tamago.android.resourceviewer.viewer.ArrayFragment;
import net.onpu_tamago.android.resourceviewer.viewer.AttrFragment;
import net.onpu_tamago.android.resourceviewer.viewer.ColorFragment;
import net.onpu_tamago.android.resourceviewer.viewer.DimensionFragment;
import net.onpu_tamago.android.resourceviewer.viewer.DrawableFragment;
import net.onpu_tamago.android.resourceviewer.viewer.StyleFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;

public class MainActivity extends ActionBarActivity implements
		ActionBar.OnNavigationListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	public static final String EXTRA_INDEX = "index";
	public static final String EXTRA_THEME = "theme";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar to show a dropdown list.
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Set up the dropdown list navigation in the action bar.
		Resources res = getResources();
		actionBar
				.setListNavigationCallbacks(
						// Specify a SpinnerAdapter to populate the dropdown
						// list.
						new ArrayAdapter<String>(actionBar.getThemedContext(),
								android.R.layout.simple_list_item_1,
								android.R.id.text1,
								res.getStringArray(R.array.title_fragments)),
						this);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getSupportActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getSupportActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		Fragment fragment = null;
		switch (position) {
		case 0:
		case 1:
			fragment = new AnimationFragment();
			break;
		case 2:
			fragment = new AnimatorFragment();
			break;
		case 3:
			fragment = new ArrayFragment();
			break;
		case 4:
			fragment = new AttrFragment();
			break;
		case 5:
			fragment = new ColorFragment();
			break;
		case 6:
			fragment = new DimensionFragment();
			break;
		case 7:
			fragment = new DrawableFragment();
			break;
		case 8:
			fragment = new StyleFragment();
			break;
		default:
			break;
		}
		Bundle args = new Bundle();
		args.putInt(EXTRA_INDEX, position);
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
		return true;
	}

}
