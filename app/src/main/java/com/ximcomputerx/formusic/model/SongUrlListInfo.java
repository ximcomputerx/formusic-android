package com.ximcomputerx.formusic.model;

import java.io.Serializable;
import java.util.List;

/**
 * @AUTHOR HACKER
 */
public class SongUrlListInfo<SongUrlInfo> implements Serializable {
    private int code;
    private String message;
    private List<SongUrlInfo> data;

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

    public List<SongUrlInfo> getData() {
        return data;
    }

    public void setData(List<SongUrlInfo> data) {
        this.data = data;
    }
}
