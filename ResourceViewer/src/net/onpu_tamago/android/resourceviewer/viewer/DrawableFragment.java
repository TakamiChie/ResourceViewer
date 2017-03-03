package net.onpu_tamago.android.resourceviewer.viewer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.onpu_tamago.android.resourceviewer.R;
import net.onpu_tamago.android.resourceviewer.classes.NameValuePair;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.androidquery.AQuery;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class DrawableFragment extends AbstractViewerFragment implements
		OnCheckedChangeListener, OnItemClickListener, OnMenuItemClickListener {

	private View mView;

	class ViewTag {
		View view;
		String name;
	}

	/**
	 * リソースを表示するためのアダプタクラス
	 * 
	 * @author 知英
	 * 
	 */
	public class ResourceListAdapter extends BaseAdapter {

		private ArrayList<NameValuePair> mList;

		private LayoutInflater mInflater;

		public ResourceListAdapter(Context context,
				ArrayList<NameValuePair> list) {
			this.mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.mList = list;
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_drawables, null);
			}
			NameValuePair nv = mList.get(position);
			AQuery $ = new AQuery(convertView);
			$.id(R.id.out_iconimage).image(nv.value);
			$.id(R.id.out_itemname).text(nv.name);
			return convertView;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_drawable, container, false);
		return mView;
	}

	@Override
	protected Class<?> getListupClass() {
		return android.R.drawable.class;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		AQuery $ = new AQuery(mView);
		ListView out_items = $.id(R.id.out_items)
				.adapter(new ResourceListAdapter(getActivity(), getIDList()))
				.getListView();
		out_items.setOnItemClickListener(this);
		((RadioGroup) $.id(R.id.edit_bktype).getView())
				.setOnCheckedChangeListener(this);

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int id) {
		int c = android.R.color.background_light;
		switch (id) {
		case R.id.edit_light:
			c = android.R.color.background_light;
			break;
		case R.id.edit_dark:
			c = android.R.color.background_dark;
			break;
		}
		ListView out_items = (ListView) mView.findViewById(R.id.out_items);
		out_items.setBackgroundResource(c);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		ViewTag tag = new ViewTag();
		tag.name = getValue(i).name;
		tag.view = view;
		adapterView.setTag(tag);
		PopupMenu popup = new PopupMenu(getActivity(), view);
		popup.inflate(R.menu.menu_drawable);
		popup.setOnMenuItemClickListener(this);
		popup.show();
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		boolean result = false;
		ListView out_items = (ListView) mView.findViewById(R.id.out_items);
		ViewTag tag = (ViewTag) out_items.getTag();
		ImageView icon_image = (ImageView) tag.view
				.findViewById(R.id.out_iconimage);
		File path = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"ResourceViewer");
		if (!path.exists()) {
			path.mkdir();
		}
		FileOutputStream output = null;
		try {
			output = new FileOutputStream(new File(path, tag.name + ".png"));

			Bitmap saveBitmap = null;
			icon_image.setDrawingCacheEnabled(true);
			switch (item.getItemId()) {
			case R.id.op_saveicon:
				icon_image.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
				result = true;
				break;
			case R.id.op_saveiconbackground:
				RadioGroup edit_bktype = (RadioGroup) mView.findViewById(R.id.edit_bktype);
				icon_image.setDrawingCacheBackgroundColor(edit_bktype.getCheckedRadioButtonId() == R.id.edit_dark ? Color.BLACK : Color.WHITE);
				result = true;
				break;
			default:
				break;
			}
			if (result) {
				saveBitmap = Bitmap.createBitmap(icon_image.getDrawingCache());
				saveBitmap.compress(CompressFormat.PNG, 100, output);
				output.flush();
				Toast.makeText(getActivity(), R.string.wording_save_success, Toast.LENGTH_SHORT).show();
			}
		} catch (IOException e) {
			Toast.makeText(getActivity(), 
					getString(R.string.wording_save_failed, e.getMessage()), 
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} finally {
			icon_image.setDrawingCacheEnabled(false);
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
}