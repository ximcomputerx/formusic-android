package com.ximcomputerx.formusic.model;

import java.io.Serializable;
import java.util.List;

public class SearchListInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    public class Result implements Serializable {
        private static final long serialVersionUID = 1L;
        private String songCount;
        private List<SearchInfo> songs;

        public String getSongCount() {
            return songCount;
        }

        public void setSongCount(String songCount) {
            this.songCount = songCount;
        }

        public List<SearchInfo> getSongs() {
            return songs;
        }

        public void setSongs(List<SearchInfo> songs) {
            this.songs = songs;
        }
    }

    private int code;
    private String message;
    private Result result;

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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
