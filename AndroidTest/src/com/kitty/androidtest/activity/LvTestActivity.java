package com.kitty.androidtest.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class LvTestActivity extends Activity{
	
	private ListView lv;
	private List<HashMap<String, String>> data;
	private SimpleAdapter sa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resolution_main);
		lv=(ListView) findViewById(R.id.lv);
		
		data=new ArrayList<HashMap<String,String>>();
		for(int i=0;i<100;i++){
			HashMap<String,String> map=new HashMap<String,String>();
			map.put("key", i+"");
			data.add(map);
		}
		
		sa=new SimpleAdapter(this, data, android.R.layout.simple_list_item_1, new String[]{"key"}, new int[]{android.R.id.text1});
		lv.setAdapter(sa);
		lv.setSelection(50);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
				System.out.println(position+" clicked");
			}
		});
	}
	
	

}
