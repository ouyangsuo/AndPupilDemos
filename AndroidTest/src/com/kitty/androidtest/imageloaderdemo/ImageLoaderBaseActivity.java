/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.kitty.androidtest.imageloaderdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.kitty.androidtest.activity.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public abstract class ImageLoaderBaseActivity extends Activity {

	protected ImageLoader imageLoader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initImageLoader();
	}

	private void initImageLoader() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
			.threadPriority(Thread.NORM_PRIORITY - 2)
			.denyCacheImageMultipleSizesInMemory()
			.discCacheFileNameGenerator(new Md5FileNameGenerator())
			.tasksProcessingOrder(QueueProcessingType.LIFO)
			.writeDebugLogs() // Remove for release app
			.build();
		
		// Initialize ImageLoader with configuration.
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 锟斤拷锟截菜碉拷
		getMenuInflater().inflate(R.menu.ild_main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.item_clear_memory_cache:
				imageLoader.clearMemoryCache();		// 锟斤拷锟斤拷诖婊猴拷锟�				
				return true;
			case R.id.item_clear_disc_cache:
				imageLoader.clearDiscCache();		// 锟斤拷锟絊D锟斤拷锟叫的伙拷锟斤拷
				return true;
			default:
				return false;
		}
	}
}
