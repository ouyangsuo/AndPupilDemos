package com.kitty.androidtest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class GetResolutionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.resolution_main);
		
		DisplayMetrics dm= new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		String resolution="手机分辨率为"+dm.widthPixels+"X"+dm.heightPixels;
		TextView tx =(TextView) findViewById(R.id.tv_resolution);
		tx.setText(resolution);
		
		super.onCreate(savedInstanceState);
	}
}
