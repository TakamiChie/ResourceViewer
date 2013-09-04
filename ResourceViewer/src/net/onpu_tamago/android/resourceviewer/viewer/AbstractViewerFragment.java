package net.onpu_tamago.android.resourceviewer.viewer;

import java.lang.reflect.Field;
import java.util.ArrayList;

import net.onpu_tamago.android.resourceviewer.MainActivity;
import net.onpu_tamago.android.resourceviewer.classes.NameValuePair;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

public abstract class AbstractViewerFragment extends Fragment {

	private static final String TAG = "[Abstract]ResourceViewer";
	private ArrayList<NameValuePair> mIds;

	/**
	 * 値をリストアップするクラス
	 * @return クラスオブジェクト
	 */
	protected abstract Class<?> getListupClass();
	
	/**
	 * IDリストを取得する
	 * @return IDリスト
	 */
	protected ArrayList<NameValuePair> getIDList(){
		return mIds;
	}
	
	/**
	 * 値を取得する
	 * @param position 項目インデックス
	 * @return 値
	 */
	protected NameValuePair getValue(int position){
		return mIds.get(position);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Field[] fields = getListupClass().getFields();
		mIds = new ArrayList<NameValuePair>();
		boolean animation = getArguments().getInt(MainActivity.EXTRA_INDEX, 0) == 0;

		for (Field f : fields) {
			try {
				boolean add;
				boolean interpolator = f.getName().contains("interpolator");
				if (animation) {
					add = !interpolator;
				} else {
					add = interpolator;
				}
				if (add) {
					Log.d(TAG, f.getName());
					mIds.add(new NameValuePair(f.getName(), f.getInt(null)));
				}
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}

		
	}
}
