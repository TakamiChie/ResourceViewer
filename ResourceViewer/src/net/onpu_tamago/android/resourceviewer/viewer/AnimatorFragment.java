package net.onpu_tamago.android.resourceviewer.viewer;

import net.onpu_tamago.android.resourceviewer.R;
import net.onpu_tamago.android.resourceviewer.classes.NameValuePair;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;

import com.androidquery.AQuery;

@SuppressLint("NewApi")
public class AnimatorFragment extends AbstractViewerFragment implements OnItemSelectedListener {

	private View mView;

	@Override
	protected Class<?> getListupClass() {
		return android.R.animator.class;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_animation, container, false);
		return mView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		AQuery $ = new AQuery(mView);

		$.id(R.id.in_animationtype)
				.adapter(
						new ArrayAdapter<NameValuePair>(getActivity(),
								android.R.layout.simple_spinner_item,
								getIDList())).getSpinner()
				.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterview, View v,
			int position, long id) {
		NameValuePair pair = getValue(position);
		View view = mView.findViewById(R.id.out_animate);
		Animator animator = AnimatorInflater.loadAnimator(getActivity(), pair.value);
		animator.setTarget(view);
		animator.start();
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterview) {
		onItemSelected(adapterview, null, 0, 0);
	}
	
}
