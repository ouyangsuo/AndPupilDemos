package com.kitty.androidtest.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;

import com.kitty.mywidget.widget.MyGif;

public class TestMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resolution_main);
		
		Drawable d1=getResources().getDrawable(R.drawable.loading_1);
		Drawable d2=getResources().getDrawable(R.drawable.loading_2);
		Drawable d3=getResources().getDrawable(R.drawable.loading_3);
		Drawable d4=getResources().getDrawable(R.drawable.loading_4);
		Drawable d5=getResources().getDrawable(R.drawable.loading_5);
		Drawable d6=getResources().getDrawable(R.drawable.loading_6);
		Drawable d7=getResources().getDrawable(R.drawable.loading_7);
		Drawable d8=getResources().getDrawable(R.drawable.loading_8);
		Drawable[] drawables=new Drawable[]{d1,d2,d3,d4,d5,d6,d7,d8};
		MyGif mg=new MyGif(this, 8, drawables, 100, 100, 200);
				
//		llRoot.addView(mg);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
