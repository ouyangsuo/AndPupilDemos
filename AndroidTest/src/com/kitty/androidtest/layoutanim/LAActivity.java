package com.kitty.androidtest.layoutanim;

import com.kitty.androidtest.activity.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;

public class LAActivity extends Activity {

	private LinearLayout llRoot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.la_activity);

		llRoot = (LinearLayout) findViewById(R.id.llRoot);

		Animation anim = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
		LayoutAnimationController lac = new LayoutAnimationController(anim, 0.3f);
		lac.setOrder(LayoutAnimationController.ORDER_NORMAL);

		llRoot.setLayoutAnimation(lac);//listview.setLayoutAnimation(lac);

		for (int i = 0; i < llRoot.getChildCount(); i++) {
			View v = llRoot.getChildAt(i);
			if (v instanceof Button) {
				v.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						llRoot.removeView(arg0);
					}
				});
			}
		}
		
	}

}
