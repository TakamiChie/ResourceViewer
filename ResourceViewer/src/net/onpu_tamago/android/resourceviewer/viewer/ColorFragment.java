package net.onpu_tamago.android.resourceviewer.viewer;

import java.util.ArrayList;

import net.onpu_tamago.android.resourceviewer.R;
import net.onpu_tamago.android.resourceviewer.classes.NameValuePair;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;

public class ColorFragment extends AbstractViewerFragment {

	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_list, container, false);
		return mView;
	}

	@Override
	protected Class<?> getListupClass() {
		return android.R.color.class;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		AQuery $ = new AQuery(mView);
		$.id(R.id.out_items).adapter(
				new ColorAdapter(getActivity(), getIDList()));
	}

	class ColorAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		private ArrayList<NameValuePair> mItems;

		public ColorAdapter(Context context, ArrayList<NameValuePair> list) {
			this.mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.mItems = list;
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public Object getItem(int position) {
			return mItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_colors, null);
			}
			NameValuePair nv = mItems.get(position);
			AQuery $ = new AQuery(convertView);
			$.id(R.id.out_itemname).text(nv.name);
			Log.d("", nv.name);
			try{
				$.id(R.id.out_color).background(nv.value);
			}catch(NotFoundException e){
				$.id(R.id.out_color).backgroundColor(getResources().getColor(nv.value));
			}
			return convertView;
		}

	}
}
