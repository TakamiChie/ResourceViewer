package net.onpu_tamago.android.resourceviewer.viewer;

import java.lang.reflect.Field;

import net.onpu_tamago.android.resourceviewer.MainActivity;
import net.onpu_tamago.android.resourceviewer.R;
import net.onpu_tamago.android.resourceviewer.classes.NameValuePair;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;

import com.androidquery.AQuery;

/**
 * アニメーション・インターポレーターを確認する際のフラグメント
 * 
 * @author 知英
 * 
 */
public class AnimationFragment extends AbstractViewerFragment implements
		OnItemSelectedListener, OnClickListener {

	@SuppressWarnings("unused")
	private static final String TAG = "[Animation]ResourceViewer";
	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_animation, container, false);
		return mView;
	}

	@Override
	protected Class<?> getListupClass() {
		return android.R.anim.class;
	}

	@Override
	protected boolean filtering(Field field) {
		boolean add;
		boolean interpolator = field.getName().contains("interpolator");
		boolean animation = getArguments().getInt(MainActivity.EXTRA_INDEX, 0) == 0;

		if (animation) {
			add = !interpolator;
		} else {
			add = interpolator;
		}
		return add;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		AQuery $ = new AQuery(mView);

		$.id(R.id.in_animationtype)
				.adapter(
						new ArrayAdapter<NameValuePair>(getActivity(),
								android.R.layout.simple_list_item_1,
								getIDList())).getSpinner()
				.setOnItemSelectedListener(this);
		$.id(R.id.op_replay).clicked(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.op_replay:
			AQuery $ = new AQuery(mView);

			animation($.id(R.id.in_animationtype).getSelectedItemPosition());
			break;

		default:
			break;
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> adapterview, View view,
			int position, long id) {
		animation(position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterview) {
		onItemSelected(adapterview, null, 0, 0);
	}

	private void animation(int position) {
		NameValuePair pair = getValue(position);

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

}
