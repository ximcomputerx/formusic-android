package com.ximcomputerx.formusic.model;

import java.io.Serializable;

public class SongUrlInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
    "br": 320000,
    "canExtend": false,
    "code": 200,
    "encodeType": "mp3",
    "expi": 1200,
    "fee": 0,
    "flag": 128,
    "gain": 0,
    "id": 33894312,
    "level": "exhigh",
    "md5": "a8772889f38dfcb91c04da915b301617",
    "payed": 0,
    "size": 10691439,
    "type": "mp3",
    "url": "http://m7.music.126.net/20200109143427/98918126509c181907df750b9d46c8d9/ymusic/0fd6/4f65/43ed/a8772889f38dfcb91c04da915b301617.mp3"
    */

    private String id;
    private String br;
    private String code;
    private String size;
    private String type;
    private String url;
    private int fee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBr() {
        return br;
    }

    public void setBr(String br) {
        this.br = br;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}
