package com.kitty.androidtest.upnpserver;

import org.fourthline.cling.support.contentdirectory.AbstractContentDirectoryService;
import org.fourthline.cling.support.contentdirectory.ContentDirectoryErrorCode;
import org.fourthline.cling.support.contentdirectory.ContentDirectoryException;
import org.fourthline.cling.support.contentdirectory.DIDLParser;
import org.fourthline.cling.support.model.BrowseFlag;
import org.fourthline.cling.support.model.BrowseResult;
import org.fourthline.cling.support.model.DIDLContent;
import org.fourthline.cling.support.model.PersonWithRole;
import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.SortCriterion;
import org.fourthline.cling.support.model.item.MusicTrack;
import org.seamless.util.MimeType;

import com.kitty.androidtest.model.Music;

public class MP3ContentDirectory extends AbstractContentDirectoryService {

	@Override
	public BrowseResult browse(String objectID, BrowseFlag browseFlag, String filter, long firstResult, long maxResults, SortCriterion[] orderby) throws ContentDirectoryException {
		try {

			// This is just an example... you have to create the DIDL content dynamically!
			DIDLContent didl = new DIDLContent();
			MimeType mimeType = new MimeType("audio", "mpeg");
			
			// 101 is the Item ID, 3 is the parent Container ID
			didl.addItem(new MusicTrack("101", "3", "北京北京", "汪峰", "未知", new PersonWithRole("汪峰", "Performer"), new Res(mimeType, 123456l, "00:03:25", 8192l, "http://zhangmenshiting.baidu.com/data2/music/35429401/15758731393286461128.mp3?xcode=0039ceae7d84e897f39e50d28c1f812b0c22ce58ba02e246")));
			didl.addItem(new MusicTrack("102", "3", "痴痴地等", "蔡琴", "未知", new PersonWithRole("蔡琴", "Performer"), new Res(mimeType, 123456l, "00:04:11", 8192l, "http://zhangmenshiting.baidu.com/data2/music/716331/716325248400128.mp3?xcode=89e758dc5a4b5059b285c0e051d4d9b549987092ec947bd0")));

			// Create more tracks...
			
			// Count and total matches is 2
			return new BrowseResult(new DIDLParser().generate(didl), 2, 2);

		} catch (Exception ex) {
			throw new ContentDirectoryException(ContentDirectoryErrorCode.CANNOT_PROCESS, ex.toString());
		}
	}

	@Override
	public BrowseResult search(String containerId, String searchCriteria, String filter, long firstResult, long maxResults, SortCriterion[] orderBy) throws ContentDirectoryException {
		// You can override this method to implement searching!
		return super.search(containerId, searchCriteria, filter, firstResult, maxResults, orderBy);
	}
}