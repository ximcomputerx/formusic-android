package com.ximcomputerx.formusic.model;

import java.io.Serializable;

/**
 * @AUTHOR HACKER
 */
public class RemoteReturnData<DataType> implements Serializable {
    private int code;
    private String message;
    private DataType data;

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
}
