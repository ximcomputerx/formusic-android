package com.ximcomputerx.formusic.model;

import java.io.Serializable;

/**
 * @AUTHOR HACKER
 */
public class LrcListInfo<LrcInfo> implements Serializable {
    private int code;
    private String message;
    private LrcInfo lrc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LrcInfo getLrc() {
        return lrc;
    }

    public void setLrc(LrcInfo lrc) {
        this.lrc = lrc;
    }
}
