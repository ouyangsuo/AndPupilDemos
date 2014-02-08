package com.kitty.androidtest.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class GoneWhileClickActivity extends Activity {

	private Button btn;
	private ListView lv;
	
	private List<HashMap<String,Integer>> data=new ArrayList<HashMap<String,Integer>>();
	private SimpleAdapter adapter;
	
	private final int WHAT = 0;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT:
//				btn.setVisibility(View.VISIBLE);
				((View)msg.obj).setVisibility(View.VISIBLE);
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gone_while_click);
		btn = (Button) findViewById(R.id.btn);
		
		for (int i = 0; i < 5; i++) {
			HashMap<String, Integer> map=new HashMap<String, Integer>();
			map.put("key", i);
			data.add(map);
		}
		
		lv = (ListView) findViewById(R.id.lv);
		adapter=new SimpleAdapter(this, data, R.layout.item, new String[]{"key"}, new int[]{R.id.tv_item});
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				view.setVisibility(View.GONE);
				
				Message msg=handler.obtainMessage(WHAT);
				msg.obj=view;
				handler.sendMessageDelayed(msg, 2000);
			}
		});

//		btn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				btn.setVisibility(View.GONE);
//				handler.sendMessageDelayed(handler.obtainMessage(WHAT), 2000);
//			}
//		});
	}

}
