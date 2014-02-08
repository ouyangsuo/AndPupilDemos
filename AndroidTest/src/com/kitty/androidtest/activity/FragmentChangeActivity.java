package com.kitty.androidtest.activity;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.kitty.androidtest.slidingmenu.BaseActivity;
import com.kitty.androidtest.slidingmenu.ColorFragment;
import com.kitty.androidtest.slidingmenu.ColorMenuFragment;
import com.kitty.androidtest.slidingmenu.MainFragment;
import com.kitty.androidtest.slidingmenu.PagerFragment;
//import com.kitty.mywidget.widget.R;
//import com.union.androidtest.activity.R;

public class FragmentChangeActivity extends BaseActivity {

	private Context context;
	private Fragment mContent;
	private ViewPager vp;
	private ArrayList<Fragment> mFragments;

	public FragmentChangeActivity() {
		super(R.string.changing_fragments);
		context=this;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);			

		initMcontent(savedInstanceState);
		initViewPager();
		setContentView(vp);		

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new ColorMenuFragment()).commit();

		// customize the SlidingMenu
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	private void initMcontent(Bundle savedInstanceState) {
		// set the Above View
		if (savedInstanceState != null){
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		}
			
		if (mContent == null){
			mContent = new ColorFragment(R.color.red);
		}
		
		// set the Above View
//		setContentView(R.layout.content_frame);
//		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mContent).commit();
	}

	private void initViewPager() {
		vp = new ViewPager(this);
		vp.setId("VP".hashCode());
		vp.setAdapter(new ColorPagerAdapter(getSupportFragmentManager()));
//		setContentView(vp);

		vp.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) { }

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) { }

			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
					break;
				default:
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
					break;
				}
			}

		});
		
		vp.setCurrentItem(0);
//		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	public void switchContent(Fragment fragment) {
		((MainFragment)mFragments.get(0)).switchContent(this,fragment);
//		mContent = fragment;
//		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
//		getSlidingMenu().showContent();
	}
	
	public class ColorPagerAdapter extends FragmentPagerAdapter {

		private final int[] COLORS = new int[] {
//			R.color.red,
			R.color.green,
			R.color.blue,
			R.color.white,
			R.color.black
		};
		
		public ColorPagerAdapter(FragmentManager fm) {
			super(fm);
			mFragments = new ArrayList<Fragment>();
//			mFragments.add(mContent);
//			for (int color : COLORS){
//				mFragments.add(new ColorFragment(color));
//			}	
			MainFragment pager0=new MainFragment(context,mContent);
//			pager0.switchContent(FragmentChangeActivity.this, mContent);		
			mFragments.add(pager0);
			mFragments.add(new PagerFragment(context, "PAGER1"));			
			mFragments.add(new PagerFragment(context, "PAGER2"));			
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.dispatchTouchEvent(ev);
	}

}
