package net.onpu_tamago.android.resourceviewer.viewer;

import java.util.ArrayList;
import java.util.HashMap;

import net.onpu_tamago.android.resourceviewer.R;
import net.onpu_tamago.android.resourceviewer.classes.NameValuePair;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.androidquery.AQuery;

public class ArrayFragment extends AbstractViewerFragment {

	private static final String NAME = "name";
	private static final String VALUE = "value";
	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_list, container,
				false);
		return mView;
	}

	@Override
	protected Class<?> getListupClass() {
		return android.R.array.class;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		ArrayList<NameValuePair> pairs = getIDList();
		Resources res = getResources();
		for (NameValuePair pair : pairs) {
			HashMap<String, String> item = new HashMap<String, String>();
			String[] items = res.getStringArray(pair.value);
			StringBuffer buffer = new StringBuffer();
			for (String s : items) {
				buffer.append(s);
				buffer.append("\n");
			}
			item.put(NAME, pair.name);
			item.put(
					VALUE,
					buffer.length() > 0 ? buffer.substring(0,
							buffer.length() - 1) : buffer.toString());
			list.add(item);
		}

		AQuery $ = new AQuery(mView);
		$.id(R.id.out_items).adapter(
				new SimpleAdapter(getActivity(), list,
						android.R.layout.simple_list_item_2, new String[] {
								NAME, VALUE }, new int[] { android.R.id.text1,
								android.R.id.text2 }));
	}
}
