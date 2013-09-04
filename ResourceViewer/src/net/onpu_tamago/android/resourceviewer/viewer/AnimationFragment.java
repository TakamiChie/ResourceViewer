package net.onpu_tamago.android.resourceviewer.viewer;

import java.lang.reflect.Field;
import java.util.ArrayList;

import net.onpu_tamago.android.resourceviewer.MainActivity;
import net.onpu_tamago.android.resourceviewer.R;
import net.onpu_tamago.android.resourceviewer.classes.NameValuePair;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;

import com.androidquery.AQuery;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class AnimationFragment extends Fragment implements
		OnItemSelectedListener {

	private static final String TAG = "[Animation]ResourceViewer";
	private View mView;
	private ArrayList<NameValuePair> mIds;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_animation, container, false);
		return mView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Field[] fields = android.R.anim.class.getFields();
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

		AQuery $ = new AQuery(mView);

		$.id(R.id.in_animationtype)
				.adapter(
						new ArrayAdapter<NameValuePair>(getActivity(),
								android.R.layout.simple_spinner_dropdown_item,
								mIds)).getSpinner()
				.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterview, View view,
			int position, long id) {
		NameValuePair pair = mIds.get(position);

		Animation animation;
		if (pair.name.contains("interpolator")) {
			// インターポーレータ
			animation = AnimationUtils.loadAnimation(getActivity(),
					R.anim.demo_animation);
			animation.setInterpolator(getActivity(), pair.value);
			animation.setRepeatMode(Animation.REVERSE);
		} else {
			// アニメーション
			animation = AnimationUtils.loadAnimation(getActivity(), pair.value);
			animation.setRepeatMode(Animation.REVERSE);
		}

		AQuery $ = new AQuery(mView);
		$.id(R.id.out_animate).animate(animation);
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterview) {
		onItemSelected(adapterview, null, 0, 0);
	}

}
