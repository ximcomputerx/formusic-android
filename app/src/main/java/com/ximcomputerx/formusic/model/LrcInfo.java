package com.ximcomputerx.formusic.model;

import java.io.Serializable;

/**
 * @AUTHOR HACKER
 */
public class LrcInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String lyric;
    private String version;

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
