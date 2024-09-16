package com.tmb.mediainfo;

public enum MimeType {
    AAC("audio/aac"),
    APNG("image/apng"),
    AVIF("image/avif"),
    AVI("video/x-msvideo"),
    BMP("image/bmp"),
    GIF("image/gif"),
    ICO("image/vnd.microsoft.icon"),
    JPEG("image/jpeg"),
    JPG("image/jpeg"),
    MID("audio/midi"),
    MIDI("audio/x-midi"),
    MP3("audio/mpeg"),
    MP4("video/mp4"),
    MPEG("video/mpeg"),
    OGA("audio/ogg"),
    OGV("video/ogg"),
    OPUS("audio/ogg"),
    PNG("image/png"),
    PDF("application/pdf"),
    SVG("image/svg+xml"),
    TIFF("image/tiff"),
    TIF("image/tiff"),
    TS("video/mp2t"),
    WAV("audio/wav"),
    WEBA("audio/webm"),
    WEBM("video/webm"),
    WEBP("image/webp");

    private final String mimeType;

    MimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }
}
