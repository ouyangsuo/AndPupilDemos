package com.kitty.androidtest.slidingmenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.kitty.androidtest.activity.R;

public class PagerFragment extends Fragment {

	private Context context;
	private String pagerName;
	private FrameLayout fl;
	
	public PagerFragment(Context context,String pagerName){
		this.context=context;
		this.pagerName=pagerName;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fl=(FrameLayout) LayoutInflater.from(context).inflate(R.layout.content_frame, null);
		
		View v=LayoutInflater.from(context).inflate(R.layout.pager_fragment, null);
		TextView tvPagername=(TextView) v.findViewById(R.id.tv_pagername);
		tvPagername.setText(pagerName);
		
		fl.addView(v);
		return fl;
	}

	public void switchContent(SlidingFragmentActivity sfActivity, Fragment fragment) {
		System.out.println("fragment instanceof ColorFragment:"+(fragment instanceof ColorFragment));
		System.out.println("fragment==null:"+(fragment==null));
		System.out.println("getChildFragmentManager()==null:"+(getChildFragmentManager()==null));
		System.out.println("getChildFragmentManager().beginTransaction()==null:"+(getChildFragmentManager().beginTransaction()==null));
		
//		fl.removeAllViews();
		getChildFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		sfActivity.getSlidingMenu().showContent();
	}
}
