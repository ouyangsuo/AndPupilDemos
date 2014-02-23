package com.kitty.androidtest.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity{

	private Context context;
	private ListView lvMain;
	private String[] demoNames;
	private String[] demoDescs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context=this;
		demoNames=getResources().getStringArray(R.array.demo_names);
		demoDescs=getResources().getStringArray(R.array.demo_descs);
		
		setContentView(R.layout.main);
		initComponent();
		initLvMain();
	}

	private void initComponent() {
		lvMain=(ListView) findViewById(R.id.lv_main);
	}
	

	private void initLvMain() {
		lvMain.setAdapter(new lvMainAdapter());
		lvMain.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if("查看设备分辨率".equals(demoNames[position])){
					context.startActivity(new Intent(context, GetResolutionActivity.class));
				}
				
				else if("异步加载图片".equals(demoNames[position])){
					context.startActivity(new Intent(context, ThreadTestActivity.class));
				}
				
				else if("使用ViewPager".equals(demoNames[position])){
					context.startActivity(new Intent(context, WeiBoActivity.class));
				}
				
				else if("使用第三方类库：SlidingMenu".equals(demoNames[position])){
					context.startActivity(new Intent(context, FragmentChangeActivity.class));
				}
				
				else if("使用第三方类库：DragSortListView".equals(demoNames[position])){
					context.startActivity(new Intent(context, TestBedDSLV.class));
				}
				
				else if("使用第三方类库：ImageLoader".equals(demoNames[position])){
					context.startActivity(new Intent(context, ImageLoaderActivity.class));
				}
				
			}
		});
	}
	
	class lvMainAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return demoNames.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			
			if (convertView == null || convertView.getTag() == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.lvmain_item, null);
				holder = new Holder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			holder.tvName.setText(demoNames[position]);
			holder.tvDesc.setText(demoDescs[position]);

			return convertView;
		}
		
	}
	
	class Holder {
		private TextView tvName;
		private TextView tvDesc;

		public Holder(View convertView) {
			tvName = (TextView) convertView.findViewById(R.id.tv_name);
			tvDesc = (TextView) convertView.findViewById(R.id.tv_desc);
		}
	}
	
}
