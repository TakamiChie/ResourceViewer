package net.onpu_tamago.android.resourceviewer.viewer;

import net.onpu_tamago.android.resourceviewer.LayoutViewerActivity;
import net.onpu_tamago.android.resourceviewer.MainActivity;
import net.onpu_tamago.android.resourceviewer.R;
import net.onpu_tamago.android.resourceviewer.classes.NameValuePair;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import com.androidquery.AQuery;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class LayoutFragment extends AbstractViewerFragment implements OnItemClickListener {

	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_style, container, false);
		return mView;
	}

	@Override
	protected Class<?> getListupClass() {
		return android.R.layout.class;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		AQuery $ = new AQuery(mView);
		$.id(R.id.out_items).adapter(
				new ArrayAdapter<NameValuePair>(getActivity(),
						android.R.layout.simple_list_item_1, getIDList())).itemClicked(this);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		Intent intent = new Intent(getActivity(), LayoutViewerActivity.class);
		intent.putExtra(MainActivity.EXTRA_LAYOUT, getValue(position).value);
		startActivity(intent);
	}
}