package com.ximcomputerx.formusic.model;

import java.io.Serializable;
import java.util.List;

/**
 * @AUTHOR HACKER
 */
public class RadioDetailListInfo<DataType> implements Serializable {
    private int code;
    private String message;
    private DataType data;
    private List<RadioDetailInfo> programs;
    private boolean more;

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

    public List<RadioDetailInfo> getPrograms() {
        return programs;
    }

    public void setPrograms(List<RadioDetailInfo> programs) {
        this.programs = programs;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }
}
