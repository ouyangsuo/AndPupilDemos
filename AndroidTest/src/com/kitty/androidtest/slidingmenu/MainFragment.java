package com.kitty.androidtest.slidingmenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.kitty.androidtest.activity.R;

public class MainFragment extends Fragment {

	private Context context;
	private FrameLayout fl;
	private Fragment defaultFragment;

	public MainFragment(Context context, Fragment fragment) {
		this.context = context;
		this.defaultFragment = fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		fl = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.content_frame, null);
		
		switchContent((SlidingFragmentActivity) context, defaultFragment);
		// fl.setBackgroundColor(getResources().getColor(R.color.red));
		
		return fl;
	}

	public void switchContent(SlidingFragmentActivity sfActivity, Fragment fragment) {
		this.defaultFragment = fragment;
		
		getChildFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		sfActivity.getSlidingMenu().showContent();
	}
}
