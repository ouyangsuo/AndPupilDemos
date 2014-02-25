package com.kitty.androidtest.model;

import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.item.Item;
import org.fourthline.cling.support.model.item.MusicTrack;

public class Music extends MusicTrack{
	
	@Override
	public String toString() {
		String url=getResources().get(0).getValue();
		if(url.length()>20){
			url=url.substring(0, 20)+"...";
		}
		return getTitle()+","+getArtists()[0]+"\n"+url+"\n\n";
	}
}
