package com.ximcomputerx.formusic.model;

import java.io.Serializable;
import java.util.List;

public class RecommendListIinfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code;
    private String message;
    private List<RecommendIinfo> result;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public List<RecommendIinfo> getResult() {
        return result;
    }

    public void setResult(List<RecommendIinfo> result) {
        this.result = result;
    }
}

