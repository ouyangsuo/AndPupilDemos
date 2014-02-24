package com.kitty.androidtest.util;

import com.kitty.androidtest.upnpclient.LightControllerActivity;

import android.content.Context;
import android.widget.Toast;

public class PowerfulBigMan {

	public static Context context;
	public static long lastItemClickTime = 0;
	public static long currentItemClickTime = 0;

	// private static String[] pieces=new
	// String[]{"点太快啦，歇会再点~","还能点更快吗⊙﹏⊙","点太快断手指","STOP狂点！！","对人家温柔点啦~"};

	/* 点击间隔大于一秒返回true，否则不予理会 */
	public static boolean testClickInterval() {
		lastItemClickTime = currentItemClickTime;
		currentItemClickTime = System.currentTimeMillis();
		if ((currentItemClickTime - lastItemClickTime) < 500) {
			Toast.makeText(context, "请不要连续点击！", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

}
