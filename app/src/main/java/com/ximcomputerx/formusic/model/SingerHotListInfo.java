package com.ximcomputerx.formusic.model;

import java.io.Serializable;
import java.util.List;

public class SingerHotListInfo implements Serializable {
    private int code;
    private String message;
    private List<SongInfo> songs;

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

    public List<SongInfo> getSongs() {
        return songs;
    }

    public void setSongs(List<SongInfo> songs) {
        this.songs = songs;
    }
}
