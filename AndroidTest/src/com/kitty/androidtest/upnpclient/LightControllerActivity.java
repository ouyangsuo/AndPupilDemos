package com.kitty.androidtest.upnpclient;

import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.kitty.androidtest.activity.BrowserActivity;
import com.kitty.androidtest.activity.R;
import com.kitty.androidtest.util.PowerfulBigMan;

public class LightControllerActivity extends Activity {

	private Button btn;
	private boolean lightIsOn = false;

	private final int MSG_GET_STATUS_FAILURE = 3;
	private final int MSG_SET_TARGET_FAILURE = 4;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				lightIsOn = false;
				btn.setText("打开");
				break;

			case 1:
				lightIsOn = true;
				btn.setText("关闭");
				break;

			case MSG_GET_STATUS_FAILURE:
				Toast.makeText(LightControllerActivity.this, "获取状态失败", Toast.LENGTH_SHORT).show();
				break;

			case MSG_SET_TARGET_FAILURE:
				Toast.makeText(LightControllerActivity.this, "设置状态失败", Toast.LENGTH_SHORT).show();
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PowerfulBigMan.context=this;
		
		setContentView(R.layout.light_controller_activity);
		initComponent();
		getStatus();
	}

	private void initComponent() {
		btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!PowerfulBigMan.testClickInterval()){
					return;
				}
				
				if (lightIsOn) {
					setTarget(false);
				} else {
					setTarget(true);
				}
			}
		});
	}

	protected void setTarget(boolean newTarget) {// 从服务中拿取UPNPACTION接口，设置参数
		ActionInvocation ai = new ActionInvocation(BrowserActivity.switchPowerService.getAction("SetTarget"));
		ai.setInput("NewTargetValue", newTarget);

		BrowserActivity.upnpService.getControlPoint().execute(new ActionCallback(ai) {

			@Override
			public void success(ActionInvocation arg0) {
				getStatus();
			}

			// 返回执行失败
			@Override
			public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
				handler.sendEmptyMessage(MSG_SET_TARGET_FAILURE);
			}
		});
	}

	protected void getStatus() {
		ActionInvocation ai = new ActionInvocation(BrowserActivity.switchPowerService.getAction("GetStatus"));

		BrowserActivity.upnpService.getControlPoint().execute(new ActionCallback(ai) {

			@Override
			public void success(ActionInvocation arg0) {
				boolean resultStatus = (Boolean) arg0.getOutput("ResultStatus").getValue();
				if (resultStatus == true) {
					handler.sendEmptyMessage(1);
				} else {
					handler.sendEmptyMessage(0);
				}
			}

			// 返回执行失败
			@Override
			public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
				handler.sendEmptyMessage(MSG_GET_STATUS_FAILURE);
			}
		});
	}

}
