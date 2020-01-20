package com.ximcomputerx.formusic.model;

import java.io.Serializable;

public class RecommendIinfo implements Serializable {
    /*
    "id": 2602222983,
    "type": 0,
    "name": "精选 | 网络热歌分享",
    "copywriter": "热门推荐",
    "picUrl": "https://p1.music.126.net/zGzNJcfLZfLW7E0bkI0opg==/109951163815887214.jpg",
    "canDislike": true,
    "trackNumberUpdateTime": 1578645338713,
    "playCount": 336399456,
    "trackCount": 79,
    "highQuality": false,
    "alg": "cityLevel_A"
     */
    private String id;
    private String name;
    private String copywriter;
    private String picUrl;
    private String playCount;
    private String trackCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCopywriter() {
        return copywriter;
    }

    public void setCopywriter(String copywriter) {
        this.copywriter = copywriter;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(String trackCount) {
        this.trackCount = trackCount;
    }
}
