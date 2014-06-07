package com.kitty.androidtest.fragmentanim;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.kitty.androidtest.activity.R;

public class Fragment2 extends Fragment implements OnClickListener {

	private static final String TAG = Fragment2.class.getSimpleName();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e(TAG, "onCreateView");
		
		View view = inflater.inflate(R.layout.fragment_two, null);
		findView(view);
		
		return view;
	}

	private void findView(View view) {
		
		Button bt_back = (Button) view.findViewById(R.id.bt_back);
		
		bt_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_back:
			
			Log.e(TAG, "back to previous");
			
			//ºóÍË
			getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			
			break;

		default:
			break;
		}
	}
	
}
