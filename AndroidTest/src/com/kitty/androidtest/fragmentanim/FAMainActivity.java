package com.kitty.androidtest.fragmentanim;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;

import com.kitty.androidtest.activity.R;
import com.kitty.androidtest.fragmentanim.Fragment1.OnNewFragemntListener;

public class FAMainActivity extends FragmentActivity implements OnNewFragemntListener {
	
	private static final String TAG = FAMainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findView();
	}

	private void findView() {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.setCustomAnimations(R.anim.push_left_in,R.anim.push_left_in); 
		
		Fragment1 oneFragment = (Fragment1) manager.findFragmentByTag("one");
		if(oneFragment==null){
			oneFragment = new Fragment1();
			Log.e(TAG, "new OneFragment");
		}
		
		ft.replace(R.id.fl_container, oneFragment, "one");
		ft.commit();
	}

	@Override
	public void onNewFragemnt() {
		
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		//设置替换和退栈的动画
		ft.setCustomAnimations(R.anim.push_left_in,R.anim.push_left_in,R.anim.back_left_in,R.anim.back_right_out); 
//		ft.setCustomAnimations(R.anim.push_left_in,R.anim.push_left_in); 
		
		Fragment2 twoFragment = (Fragment2) manager.findFragmentByTag("two");
		if(twoFragment==null){
			Log.e(TAG, "new TwoFragment");
			twoFragment = new Fragment2();
		}
		
		ft.replace(R.id.fl_container, twoFragment, "two");
		
		ft.addToBackStack(null);
		
		ft.commit();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		FragmentManager fm = getSupportFragmentManager();
		
		int count = fm.getBackStackEntryCount();
		
		if(keyCode==KeyEvent.KEYCODE_BACK && count>0){
			
			Log.e(TAG, "back to list "+count);
			
			fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE); 
			
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
