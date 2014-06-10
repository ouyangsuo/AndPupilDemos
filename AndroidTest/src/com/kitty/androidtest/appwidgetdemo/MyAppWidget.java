package com.kitty.androidtest.appwidgetdemo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.kitty.androidtest.activity.R;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.widget.RemoteViews;

public class MyAppWidget extends AppWidgetProvider {

	private final String actionString = "com.kitty.appwidgetdemo.action.test";
	private Handler handler;

	@Override
	public void onReceive(Context context, Intent intent) {

		System.out.println("onReceive:" + intent.getAction());
		super.onReceive(context, intent);

		if (intent.getAction().equals(actionString)) {
			
			ComponentName componentName = new ComponentName(context, MyAppWidget.class);		
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.appwidget_layout);
			remoteViews.setTextViewText(R.id.tvTab, "This is a msg from Application");

			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);	
			appWidgetManager.updateAppWidget(componentName, remoteViews);
		}

	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		System.out.println("onUpdate");
		
		//�󶨵���¼�����
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.appwidget_layout);
		initListeners(context,remoteViews);		
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

		//ʹ��handlerˢ�»����˳������ֹͣ����
//		updateTimeInWidget(appWidgetManager, appWidgetIds, remoteViews);
		
		//��׼��������
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, UpdateService.class);
		PendingIntent refreshCallbackPendingIntent = PendingIntent.getService(context, 0, intent, 0);
		Time time = new Time();
		time.setToNow();
		alarmManager.setRepeating(AlarmManager.RTC, time.toMillis(true), 1000, refreshCallbackPendingIntent);
		
		context.startService(intent);
	}

	private void initListeners(Context context,RemoteViews remoteViews) {		
		// ����Intent����,�󶨰�ť����¼�
		Intent intent = new Intent(actionString);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		remoteViews.setOnClickPendingIntent(R.id.btnSend, pendingIntent);

		// �󶨱�ǩ����¼�
		Intent intent2 = new Intent(context, AppWidgetActivity.class);
		PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 0, intent2, 0);
		remoteViews.setOnClickPendingIntent(R.id.tvTab, pendingIntent2);
	}
	
	private void updateTimeInWidget(final AppWidgetManager appWidgetManager, final int[] appWidgetIds, final RemoteViews remoteViews) {

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				switch (msg.what) {
				case 0:
					// ����widget��TextView�ؼ�������
					remoteViews.setTextViewText(R.id.tvTab, String.valueOf(msg.obj));

					// ����Appwidget
					appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
					break;

				default:
					break;
				}
			}
		};

		handler.post(new Runnable() {

			@Override
			public void run() {
				Message message = new Message();
				message.what = 0;
				message.obj = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss").format(new Date());
				handler.sendMessage(message);

				handler.postDelayed(this, 1000);
			}
		});
	}
}
