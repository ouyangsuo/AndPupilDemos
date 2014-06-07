package com.kitty.androidtest.fragmentanim;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.kitty.androidtest.activity.R;

public class Fragment1 extends Fragment implements OnClickListener {

	private static final String TAG = Fragment1.class.getSimpleName();
	
	private OnNewFragemntListener mCallback;

	@Override
	public void onAttach(Activity activity) {
		try {
			mCallback = (OnNewFragemntListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnNewFragemntListener");
		}
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.e(TAG, "onCreateView");

		View view = inflater.inflate(R.layout.fragment_one, null);
		findView(view);
		
		return view;
	}

	private void findView(View view) {
		
		Button bt_open = (Button) view.findViewById(R.id.bt_open);
		bt_open.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_open:
			
			Log.e(TAG, "open TwoFragment");
			//回调Activity中的方法
			mCallback.onNewFragemnt();
			
			break;

		default:
			break;
		}
	}
	/**
	 * 后退 回调接口
	 */
	public interface OnNewFragemntListener {

		public void onNewFragemnt();
	}
	
}
