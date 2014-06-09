package com.kitty.androidtest.pull2refresh;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kitty.androidtest.activity.R;

public class PTRActivity extends Activity {

	private ArrayAdapter<String> adapter;
	private PullToRefreshListView pLv;
	private ArrayList<String> strings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ptr_activity);

		pLv = (PullToRefreshListView) findViewById(R.id.pLv);

		strings = new ArrayList<String>();
		strings.add("hello");
		strings.add("how are you");
		strings.add("bye");

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, strings);
		pLv.setAdapter(adapter);

		pLv.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... arg0) {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						List<String> temp=new ArrayList<String>();
						temp.addAll(strings);
						
						strings.clear();
						strings.add("new one");
						strings.addAll(temp);
						temp=null;
						
						adapter.notifyDataSetChanged();
						pLv.onRefreshComplete();
					}

				}.execute();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
