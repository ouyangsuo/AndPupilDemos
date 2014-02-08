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
	private String[] demos=new String[]{"�鿴�豸�ֱ���","�첽����ͼƬ","ʹ��ViewPager","ʹ�õ�������⣺SlidingMenu","ʹ�õ�������⣺XXX"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context=this;
		
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
				if("�鿴�豸�ֱ���".equals(demos[position])){
					context.startActivity(new Intent(context, GetResolutionActivity.class));
				}
				
				else if("�첽����ͼƬ".equals(demos[position])){
					context.startActivity(new Intent(context, ThreadTestActivity.class));
				}
				
				else if("ʹ��ViewPager".equals(demos[position])){
					context.startActivity(new Intent(context, WeiBoActivity.class));
				}
				
				else if("ʹ�õ�������⣺SlidingMenu".equals(demos[position])){
					context.startActivity(new Intent(context, FragmentChangeActivity.class));
				}
				
				else if("ʹ�õ�������⣺XXX".equals(demos[position])){
//					context.startActivity(new Intent(context, FragmentChangeActivity.class));
				}
				
			}
		});
	}
	
	class lvMainAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return demos.length;
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

			holder.tvName.setText(demos[position]);

			return convertView;
		}
		
	}
	
	class Holder {
		private TextView tvName;

		public Holder(View convertView) {
			tvName = (TextView) convertView.findViewById(R.id.tv_name);
		}
	}
	
}
