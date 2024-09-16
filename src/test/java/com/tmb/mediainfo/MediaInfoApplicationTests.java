package com.tmb.mediainfo;

import static org.junit.Assert.assertNotNull;

import org.springframework.boot.test.context.SpringBootTest;

import com.tmb.mediainfo.model.MediaInfo;
import com.tmb.mediainfo.service.MediaInfoService;

@SpringBootTest
public class MediaInfoApplicationTests {

	@org.junit.Test
	public void contextLoads() {
	}

	@org.junit.Test()
	public void testSampleURL() throws Exception {
		String url = "https://tmbidigitalassetsazure.blob.core.windows.net/wpvideos/10 Kitchen Cabinet Storage Ideas Upgrades to Try.mp4";
		MediaInfoService mediaInfoService = new MediaInfoService();
		assertNotNull(mediaInfoService);
		MediaInfo info = mediaInfoService.getMediaInfo(url);
		assertNotNull(info);
	}

}
