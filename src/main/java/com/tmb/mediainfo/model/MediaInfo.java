package com.tmb.mediainfo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaInfo {
    @Wither private String containerFormat;
    @Wither private double duration;
    
    @Wither private String videoFormat;
    @Wither private String videoCodec;
    @Wither private long videoBitrate;
    @Wither private float videoFramerate;
    @Wither
    public int videoWidth;
    @Wither private int videoHeight;

    @Wither private String audioFormat;
    @Wither private String audioCodec;
    @Wither private long audioBitrate;
    @Wither private int audioSamplerate;
    @Wither private String orientation;
    @Wither private String mimeType;
}
