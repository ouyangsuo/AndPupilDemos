package com.kitty.androidtest.util;

import com.kitty.androidtest.upnpclient.LightControllerActivity;

import android.content.Context;
import android.widget.Toast;

public class PowerfulBigMan {

	public static Context context;
	public static long lastItemClickTime = 0;
	public static long currentItemClickTime = 0;

	// private static String[] pieces=new
	// String[]{"��̫������Ъ���ٵ�~","���ܵ������ѩn��","��̫�����ָ","STOP��㣡��","���˼��������~"};

	/* ����������һ�뷵��true����������� */
	public static boolean testClickInterval() {
		lastItemClickTime = currentItemClickTime;
		currentItemClickTime = System.currentTimeMillis();
		if ((currentItemClickTime - lastItemClickTime) < 500) {
			Toast.makeText(context, "�벻Ҫ���������", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

}
