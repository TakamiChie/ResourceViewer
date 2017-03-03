package net.onpu_tamago.android.resourceviewer.viewer;

import net.onpu_tamago.android.resourceviewer.R;
import net.onpu_tamago.android.resourceviewer.classes.NameValuePair;
import android.annotation.SuppressLint;
import android.os.Bundle;
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
 * インターポレーターを確認する際のフラグメント
 * @author 知英
 *
 */
public class InterpolatorFragment extends AbstractViewerFragment implements
		OnItemSelectedListener {

	@SuppressWarnings("unused")
	private static final String TAG = "[Interpolator]ResourceViewer";
	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_animation, container, false);
		return mView;
	}

	@SuppressLint("NewApi")
	@Override
	protected Class<?> getListupClass() {
		return android.R.interpolator.class;
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
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterview, View view,
			int position, long id) {
		NameValuePair pair = getValue(position);

		Animation animation;
		animation = AnimationUtils.loadAnimation(getActivity(),
				R.anim.demo_animation);
		animation.setInterpolator(getActivity(), pair.value);
		animation.setRepeatMode(Animation.REVERSE);

		AQuery $ = new AQuery(mView);
		$.id(R.id.out_animate).animate(animation);
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterview) {
		onItemSelected(adapterview, null, 0, 0);
	}

}
