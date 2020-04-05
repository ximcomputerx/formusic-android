package com.ximcomputerx.formusic.model;

import java.io.Serializable;

/**
 * @AUTHOR HACKER
 */
public class VersionListInfo implements Serializable {
    private int code;
    private String message;
    private VersionInfo data;

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

    public VersionInfo getData() {
        return data;
    }

    public void setData(VersionInfo data) {
        this.data = data;
    }
}
