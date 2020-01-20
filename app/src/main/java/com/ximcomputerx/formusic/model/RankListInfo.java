package com.ximcomputerx.formusic.model;

import java.io.Serializable;
import java.util.List;

/**
 * @AUTHOR HACKER
 */
public class RankListInfo<DataType> implements Serializable {
    private int code;
    private String message;
    private DataType data;
    private List<RankInfo> list;

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

    public List<RankInfo> getList() {
        return list;
    }

    public void setList(List<RankInfo> list) {
        this.list = list;
    }
}
