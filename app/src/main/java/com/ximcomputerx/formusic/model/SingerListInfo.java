package com.ximcomputerx.formusic.model;

import java.io.Serializable;
import java.util.List;

public class SingerListInfo implements Serializable {
    private int code;
    private String message;
    private List<SingerInfo> artists;

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

    public List<SingerInfo> getArtists() {
        return artists;
    }

    public void setArtists(List<SingerInfo> artists) {
        this.artists = artists;
    }
}
