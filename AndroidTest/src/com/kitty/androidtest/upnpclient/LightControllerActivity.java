package com.kitty.androidtest.upnpclient;

import java.util.ArrayList;

import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.controlpoint.SubscriptionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Action;
import org.fourthline.cling.support.contentdirectory.callback.Browse;
import org.fourthline.cling.support.model.BrowseFlag;
import org.fourthline.cling.support.model.DIDLContent;
import org.fourthline.cling.support.model.item.MusicTrack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kitty.androidtest.activity.BrowserActivity;
import com.kitty.androidtest.activity.LightActivity;
import com.kitty.androidtest.activity.R;
import com.kitty.androidtest.util.PowerfulBigMan;

public class LightControllerActivity extends Activity {

	private Context context;

	private Button btn;
	private Button btnGet;
	private TextView tvDidl;
	private ListView lvDidl;
	private DidlAdapter adapter;

	private boolean lightIsOn = false;

	public static final int MSG_LIGHT_OFF = 0;
	public static final int MSG_LIGHT_ON = 1;
	private final int MSG_GET_STATUS_FAILURE = 3;
	private final int MSG_SET_TARGET_FAILURE = 4;
	private final int MSG_DIDL_CONTENT_GOT = 5;
	private final int MSG_GET_DIDL_FAILURE = 6;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_LIGHT_OFF:
				lightIsOn = false;
				btn.setText("打开");
				break;

			case MSG_LIGHT_ON:
				lightIsOn = true;
				btn.setText("关闭");
				break;

			case MSG_GET_STATUS_FAILURE:
				Toast.makeText(LightControllerActivity.this, "获取状态失败", Toast.LENGTH_SHORT).show();
				break;

			case MSG_SET_TARGET_FAILURE:
				Toast.makeText(LightControllerActivity.this, "设置状态失败", Toast.LENGTH_SHORT).show();
				break;

			case MSG_DIDL_CONTENT_GOT:
				ArrayList<MusicTrack> songs = (ArrayList<MusicTrack>) msg.obj;
				String str = "";
				for (MusicTrack m : songs) {
					str += m.getTitle() + "," + m.getArtists()[0] + "\n" + m.getResources().get(0).getValue() + "\n\n";
				}
				tvDidl.setText(str);
				
				adapter.setSongs(songs);
				adapter.notifyDataSetChanged();

				// play(didlContent);
				break;

			case MSG_GET_DIDL_FAILURE:
				tvDidl.setText("get didl failure!");
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PowerfulBigMan.context = this;
		context = this;

		setContentView(R.layout.light_controller_activity);
		initComponent();
		getStatus();
		initSub();
	}

	protected void iPlay(String url) {
		System.out.println("play locally:url="+url);
		final String myUrl=url;
		
		Intent intent = new Intent();
		Uri uri = Uri.parse(myUrl);
		intent.setDataAndType(uri, "audio/*");
		intent.setAction(Intent.ACTION_VIEW);
		context.startActivity(intent);
	}

	protected void uPlay(String url) {// 从服务中拿取UPNPACTION接口，设置参数
		ActionInvocation ai = new ActionInvocation(BrowserActivity.switchPowerService.getAction("Play"));
		ai.setInput("Url", url);

		BrowserActivity.upnpService.getControlPoint().execute(new ActionCallback(ai) {

			@Override
			public void success(ActionInvocation arg0) {
				System.out.println("uPlay success");
			}

			// 返回执行失败
			@Override
			public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
				Looper.prepare();
				Toast.makeText(context, "uPlay failure", Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		});
	}

	private void initSub() {
		SubscriptionCallback lightSub = new LightSubscription(BrowserActivity.switchPowerService);
		((LightSubscription) lightSub).getActivityHandlers().add(handler);

		BrowserActivity.upnpService.getControlPoint().execute(lightSub);
	}

	private void initComponent() {
		btn = (Button) findViewById(R.id.btn);
		btnGet = (Button) findViewById(R.id.btn_get);
		tvDidl = (TextView) findViewById(R.id.tv_didl);
		lvDidl = (ListView) findViewById(R.id.lv_didl);

		adapter = new DidlAdapter();
		lvDidl.setAdapter(adapter);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!PowerfulBigMan.testClickInterval()) {
					return;
				}

				if (lightIsOn) {
					setTarget(false);
				} else {
					setTarget(true);
				}
			}
		});

		btnGet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!PowerfulBigMan.testClickInterval()) {
					return;
				}

				getServerSharedInfo();
			}
		});
	}

	protected void getServerSharedInfo() {
		System.out.println("getServerSharedInfo");

		if (BrowserActivity.contentDirectoryService == null) {
			Toast.makeText(this, "ContentDirectory服务尚未初始化！", Toast.LENGTH_SHORT).show();
		}

		BrowserActivity.upnpService.getControlPoint().execute(new Browse(BrowserActivity.contentDirectoryService, "0", BrowseFlag.DIRECT_CHILDREN, "*", 100l, 50l, null) {

			@Override
			public void received(ActionInvocation arg0, DIDLContent didl) {
				System.out.println("Browse received");
				System.out.println("didl=" + didl.getItems().get(0));

				Message msg = handler.obtainMessage(MSG_DIDL_CONTENT_GOT);

				ArrayList<MusicTrack> songs = new ArrayList<MusicTrack>();
				for (int i = 0; i < didl.getItems().size(); i++) {
					MusicTrack m = (MusicTrack) didl.getItems().get(i);
					songs.add(m);
				}
				msg.obj = songs;

				handler.sendMessage(msg);
			}

			@Override
			public void updateStatus(Status arg0) {
				System.out.println("Browse updateStatus");
			}

			@Override
			public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
				System.out.println("Browse failure");

				Message msg = handler.obtainMessage(MSG_DIDL_CONTENT_GOT);
				msg.obj = arg1.toString();
				handler.sendMessage(msg);
			} // Descending

			// Implementation...

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
					handler.sendEmptyMessage(MSG_LIGHT_ON);
				} else {
					handler.sendEmptyMessage(MSG_LIGHT_OFF);
				}
			}

			// 返回执行失败
			@Override
			public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
				handler.sendEmptyMessage(MSG_GET_STATUS_FAILURE);
			}
		});
	}

	class DidlAdapter extends BaseAdapter {

		ArrayList<MusicTrack> songs = new ArrayList<MusicTrack>();

		public ArrayList<MusicTrack> getSongs() {
			return songs;
		}
		

		public void setSongs(ArrayList<MusicTrack> songs) {
			this.songs = songs;
		}
		

		@Override
		public int getCount() {
			return songs.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (convertView == null || convertView.getTag() == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.item_didl_music, null);
				holder = new Holder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			final MusicTrack m = songs.get(position);
			holder.tvTitle.setText(m.getTitle());
			holder.tvArtist.setText(m.getArtists()[0] + "");
			holder.btnIPlay.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					iPlay(m.getResources().get(0).getValue());
				}
			});
			holder.btnUPlay.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					uPlay(m.getResources().get(0).getValue());
				}
			});

			return convertView;
		}

	}

	class Holder {
		private TextView tvTitle;
		private TextView tvArtist;
		private Button btnIPlay;
		private Button btnUPlay;

		public Holder(View v) {
			tvTitle = (TextView) v.findViewById(R.id.tv_title);
			tvArtist = (TextView) v.findViewById(R.id.tv_artist);
			btnIPlay = (Button) v.findViewById(R.id.btn_i_play);
			btnUPlay = (Button) v.findViewById(R.id.btn_you_play);
		}
	}
}
