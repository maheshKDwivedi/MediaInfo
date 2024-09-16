package com.tmb.mediainfo;

import java.util.Arrays;

import com.tmb.mediainfo.service.MetaDataExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tmb.mediainfo.service.MediaInfoService;


@SpringBootApplication
public class MediaInfoApplication implements ApplicationRunner {
	@Autowired
	MediaInfoService mediaInfoService;

	@Autowired
	MetaDataExtractor extractMetaData;

	private static final Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

	public static void main(String[] args) {
		SpringApplication.run(MediaInfoApplication.class, args);
	}

	@Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
		extractMetaData.extractMetaData();
		/*if(args.containsOption("mediaPath")) {
        	logger.info("MediaPath: " + args.getOptionValues("mediaPath").get(0));
			MediaInfo info = mediaInfoService.getMediaInfo(args.getOptionValues("mediaPath").get(0));
			System.out.println("MediaInfo --> final result: " + info.toString());
		} else {
			logger.error("Argument mediaPath not provided");
		}*/
    }}
