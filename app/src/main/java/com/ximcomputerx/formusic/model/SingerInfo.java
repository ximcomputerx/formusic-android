package com.ximcomputerx.formusic.model;

import java.io.Serializable;

public class SingerInfo implements Serializable {
    /*
    "accountId": 277313426,
    "albumSize": 16,
    "alias": [],
    "briefDesc": "",
    "followed": false,
    "id": 12429072,
    "img1v1Id": 109951164232034480,
    "img1v1Id_str": "109951164232034479",
    "img1v1Url": "https://p2.music.126.net/Xl0WENt4F6wsgjjjQWuQsw==/109951164232034479.jpg",
    "musicSize": 73,
    "name": "隔壁老樊",
    "picId": 109951164232057950,
    "picId_str": "109951164232057952",
    "picUrl": "https://p2.music.126.net/uTwOm8AEFFX_BYHvfvFcmQ==/109951164232057952.jpg",
    "topicPerson": 0,
    "trans": ""
     */
    private String id;
    private String img1v1Url;
    private String musicSize;
    private String name;
    private String picUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg1v1Url() {
        return img1v1Url;
    }

    public void setImg1v1Url(String img1v1Url) {
        this.img1v1Url = img1v1Url;
    }

    public String getMusicSize() {
        return musicSize;
    }

    public void setMusicSize(String musicSize) {
        this.musicSize = musicSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
