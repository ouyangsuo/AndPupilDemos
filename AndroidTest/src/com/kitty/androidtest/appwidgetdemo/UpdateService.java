package com.kitty.androidtest.appwidgetdemo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.kitty.androidtest.activity.R;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class UpdateService extends Service {

	private SimpleDateFormat sdf=new SimpleDateFormat("yyyyƒÍMM‘¬dd»’ HH:mm:ss");
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override  
    public void onStart(Intent intent, int startId) {   
		System.out.println("UpdateService onStart");
		
		ComponentName widgetComponentName = new ComponentName(this, MyAppWidget.class);
		RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.appwidget_layout);
		remoteViews.setTextViewText(R.id.tvTab, sdf.format(new Date()));
		
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
		appWidgetManager.updateAppWidget(widgetComponentName, remoteViews); 
    }

}
