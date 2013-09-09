package net.onpu_tamago.android.resourceviewer.viewer;

import java.util.ArrayList;

import net.onpu_tamago.android.resourceviewer.R;
import net.onpu_tamago.android.resourceviewer.classes.NameValuePair;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androidquery.AQuery;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class AttrFragment extends AbstractViewerFragment implements
		OnItemSelectedListener, OnCheckedChangeListener {

	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_attr, container, false);
		return mView;
	}

	@Override
	protected Class<?> getListupClass() {
		return android.R.attr.class;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		AQuery $ = new AQuery(mView);
		$.id(R.id.out_items)
				.adapter(
						new ArrayAdapter<NameValuePair>(getActivity(),
								android.R.layout.simple_list_item_1,
								getIDList())).itemSelected(this);
		((RadioGroup)$.id(R.id.edit_bktype).getView()).setOnCheckedChangeListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i,
			long l) {
		int attrId = getValue(i).value;
		ArrayList<View> views = new ArrayList<View>();
		try {
			Button button = new Button(getActivity(), null, attrId);
			button.setText("Sample Button");
			views.add(button);
		} catch (IllegalArgumentException e) {

		}
		try {
			TextView text = new TextView(getActivity(), null, attrId);
			text.setText("Sample TextView");
			views.add(text);
		} catch (IllegalArgumentException e) {

		}

		try {
			EditText editText = new EditText(getActivity(), null, attrId);
			editText.setText("Sample EditText");
			views.add(editText);
		} catch (IllegalArgumentException e) {

		}
		try {
			ImageView image = new ImageView(getActivity(), null, attrId);
			image.setImageResource(R.drawable.ic_launcher);
			views.add(image);
		} catch (IllegalArgumentException e) {

		}
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 20, 0, 20);
		params.gravity = Gravity.CENTER_HORIZONTAL;
		LinearLayout frame = (LinearLayout) mView.findViewById(R.id.views);
		frame.removeAllViews();
		for (View v : views) {
			frame.addView(v, params);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {
		onItemSelected(adapterView, null, 0, 0);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int id) {
		int c = android.R.color.background_light;
		switch(id){
		case R.id.edit_light:
			c = android.R.color.background_light;
			break;
		case R.id.edit_dark:
			c = android.R.color.background_dark;
			break;
		}
		ScrollView frame = (ScrollView) mView.findViewById(R.id.views_background);
		frame.setBackgroundResource(c);
	}
}
