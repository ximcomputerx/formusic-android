package com.ximcomputerx.formusic.model;

import java.io.Serializable;
import java.util.List;

/**
 * @AUTHOR HACKER
 */
public class MixListInfo<DataType> implements Serializable {
    private int code;
    private String message;
    private DataType data;
    private List<MixInfo> playlists;
    private String cat;

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

    public DataType getData() {
        return data;
    }

    public void setData(DataType data) {
        this.data = data;
    }

    public List<MixInfo> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<MixInfo> playlists) {
        this.playlists = playlists;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
}
