package com.tmb.mediainfo.service;

import com.github.kokorin.jaffree.process.JaffreeAbnormalExitException;
import com.tmb.mediainfo.MimeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import com.github.kokorin.jaffree.StreamType;
import com.github.kokorin.jaffree.ffprobe.FFprobe;
import com.github.kokorin.jaffree.ffprobe.FFprobeResult;
import com.github.kokorin.jaffree.ffprobe.Stream;
import com.tmb.mediainfo.model.MediaInfo;

@Service
public class MediaInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

    public MediaInfo getMediaInfo(String path) throws Exception {

        MediaInfo mediaInfo = new MediaInfo();
        try {
            FFprobeResult result = FFprobe.atPath()
                    .setShowStreams(true)
                    .setShowFormat(true)
                    .setInput(path)
                    .execute();


            mediaInfo.setContainerFormat(result.getFormat().getFormatName());
            mediaInfo.setDuration(result.getFormat().getDuration());

            for (Stream stream : result.getStreams()) {
                logger.info("Stream #" + stream.getIndex()
                        + " type: " + stream.getCodecType()
                        + " duration: " + stream.getDuration() + " seconds");

                if (stream.getCodecType() == StreamType.VIDEO) {
                    mediaInfo.setVideoCodec(stream.getCodecName());
                    mediaInfo.setVideoFormat(stream.getCodecLongName());
                    mediaInfo.setVideoBitrate(stream.getBitRate());
                    mediaInfo.setVideoFramerate(stream.getRFrameRate().floatValue());
                    Integer height = stream.getHeight();
                    Integer width = stream.getWidth();
                    mediaInfo.setVideoHeight(height);
                    mediaInfo.setVideoWidth(width);
                    if (width >= height) {
                        mediaInfo.setOrientation("Landscape");
                    } else {
                        mediaInfo.setOrientation("Portrait");
                    }
                    mediaInfo.setMimeType(getMimeType(path));
                } else if (stream.getCodecType() == StreamType.AUDIO) {
                    mediaInfo.setAudioCodec(stream.getCodecName());
                    mediaInfo.setAudioFormat(stream.getCodecLongName());
                    mediaInfo.setAudioBitrate(stream.getBitRate());
                    mediaInfo.setAudioSamplerate(stream.getSampleRate());
                }
            }
        } catch (JaffreeAbnormalExitException e) {
            throw new Exception(e.getProcessErrorLogMessages().get(0).message);
        }
        return mediaInfo;
    }

    private String getMimeType(String uriPath){
        String extension = uriPath.substring(uriPath.lastIndexOf(".")+1);

        return MimeType.valueOf(extension.toUpperCase()).getMimeType();
    }

}
