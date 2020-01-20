package com.ximcomputerx.formusic.model;

import java.io.Serializable;
import java.util.List;

public class SearchHotListInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code;
    private String message;
    private List<SearchHotInfo> data;

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

    public List<SearchHotInfo> getData() {
        return data;
    }

    public void setData(List<SearchHotInfo> data) {
        this.data = data;
    }
}
