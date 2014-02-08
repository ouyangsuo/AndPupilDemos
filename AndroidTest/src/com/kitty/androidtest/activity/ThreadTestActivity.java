package com.kitty.androidtest.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ThreadTestActivity extends Activity {

	private Handler handler;
	private Button bt;
	private Handler mainHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);			
			if (msg.what == 2012) {
				// 只要在主线程就可以处理ui
				((ImageView) ThreadTestActivity.this.findViewById(msg.arg1)).setImageDrawable((Drawable) msg.obj);
			}
		}
	};

	private ExecutorService service = Executors.newFixedThreadPool(5);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thread_test_main);
		initViews();
		
		HandlerThread ht = new HandlerThread("down image thread");
		ht.start();
		
		// 如果有了looper那么这个handler就不可以处理ui�?
		handler = new Handler(ht.getLooper()) {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
			}
		};
	}

	private void initViews() {
		bt = (Button) findViewById(R.id.btn);
		bt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadImagesByExecutors("http://news.baidu.com/z/resource/r/image/2012-11-23/23c1625aca99f02c50d8e510383a34e7.jpg", R.id.iv_1);
				loadImagesByExecutors("http://news.baidu.com/z/resource/r/image/2012-11-23/c4698d97ef6d10722c8e917733c7beb3.jpg", R.id.iv_2);
				loadImagesByExecutors("http://news.baidu.com/z/resource/r/image/2012-11-23/f332ffe433be2a3112be15f78bff5a40.jpg", R.id.iv_3);
				loadImagesByExecutors("http://news.baidu.com/z/resource/r/image/2012-11-23/6ff8a9c647a1e80bc602eeda48865d4c.jpg", R.id.iv_4);
				loadImagesByExecutors("http://news.baidu.com/z/resource/r/image/2012-11-23/f104d069f7443dca52a878d779392874.jpg", R.id.iv_5);
			}
		});
	}

	/*
	 * 通过拥有looper的handler.post(runnable)，新建线�?
	 */
	private void loadImagesByHandler(final String url, final int id) {

		handler.post(new Runnable() {// 如果handler没有Looper那么它就不能构建新线程了

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.e("ThreadTestActivity","当前线程:" + Thread.currentThread().getName());
				Drawable drawable = null;
				try {
					drawable = Drawable.createFromStream(new URL(url).openStream(), "image.gif");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// SystemClock.sleep(2000);
				// ((ImageView)MainActivity.this.findViewById(id)).setImageDrawable(drawable);
				Message msg = mainHandler.obtainMessage();
				msg.what = 2012;
				msg.arg1 = id;
				msg.obj = drawable;
				msg.sendToTarget();
			}

		});

	}

	/*
	 * 通过Thread来new出多个线�?
	 */
	private void loadImagesByThread(final String url, final int id) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.e("当前线程: ",Thread.currentThread().getName());
				Drawable drawable = null;
				try {
					drawable = Drawable.createFromStream(new URL(url).openStream(), "image.gif");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg = mainHandler.obtainMessage();
				msg.what = 2012;
				msg.arg1 = id;
				msg.obj = drawable;
				msg.sendToTarget();

			}

		}).start();
	}

	/*
	 * 构建异步任务，这样就不用handler来处理消息了
	 */
	private void loadImageByAsyncTask(final String url, final int id) {
		DownloadTask task = new DownloadTask();
		task.execute("" + id, url);// AsyncTask不可重复执行
	}

	/*
	 * 用线程池下载
	 */
	private void loadImagesByExecutors(final String url, final int id) {
		service.submit(new Runnable() {
			@Override
			public void run() {
				Log.e("当前线程: ", Thread.currentThread().getName());

				try {
					final Drawable drawable = Drawable.createFromStream(new URL(url).openStream(), "image.gif");
					mainHandler.post(new Runnable() {
						@Override
						public void run() {// 这将在主线程运行
							((ImageView) ThreadTestActivity.this.findViewById(id)).setImageDrawable(drawable);
						}
					});
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		});
	}

	class DownloadTask extends AsyncTask {

		int id;

		protected void onPostExecute(Drawable result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			((ImageView) ThreadTestActivity.this.findViewById(id)).setImageDrawable(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected Drawable doInBackground(Object... params) {

			Log.e("当前线程: ", Thread.currentThread().getName());
			Drawable drawable = null;
			this.id = Integer.parseInt((String) params[0]);
			
			try {
				drawable = Drawable.createFromStream(new URL((String) params[1]).openStream(), "image.gif");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return drawable;
		}

	}
}
