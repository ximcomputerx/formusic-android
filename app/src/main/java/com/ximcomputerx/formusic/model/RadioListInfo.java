package com.ximcomputerx.formusic.model;

import java.io.Serializable;
import java.util.List;

public class RadioListInfo<DataType> implements Serializable {
    private int code;
    private String message;
    private DataType data;
    private List<RadioInfo> djRadios;
    private Boolean hasMore;

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

    public List<RadioInfo> getDjRadios() {
        return djRadios;
    }

    public void setDjRadios(List<RadioInfo> djRadios) {
        this.djRadios = djRadios;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }
}
