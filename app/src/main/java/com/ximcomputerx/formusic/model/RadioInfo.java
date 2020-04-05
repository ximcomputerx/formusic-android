package com.ximcomputerx.formusic.model;

import java.io.Serializable;

public class RadioInfo implements Serializable {
    /*
    "buyed": false,
    "category": "创作|翻唱",
    "categoryId": 2001,
    "createTime": 1552801589433,
    "desc": "向阳君超甜hing",
    "dj": {
        "accountStatus": 0,
        "authStatus": 1,
        "authority": 0,
        "avatarImgId": 109951164439561860,
        "avatarImgIdStr": "109951164439561862",
        "avatarImgId_str": "109951164439561862",
        "avatarUrl": "http://p1.music.126.net/6Yj5tcTAvKoHvJdA3UyEAg==/109951164439561862.jpg",
        "backgroundImgId": 109951162868128400,
        "backgroundImgIdStr": "109951162868128395",
        "backgroundUrl": "http://p1.music.126.net/2zSNIqTcpHL2jIvU6hG0EA==/109951162868128395.jpg",
        "birthday": -2209017600000,
        "city": 510100,
        "defaultAvatar": false,
        "description": "",
        "detailDescription": "",
        "djStatus": 10,
        "followed": false,
        "gender": 2,
        "mutual": false,
        "nickname": "我向阳君很甜",
        "province": 510000,
        "signature": "5sing: 向阳君",
        "userId": 503833386,
        "userType": 4,
        "vipType": 11
    },
    "feeScope": 0,
    "finished": false,
    "id": 792958421,
    "lastProgramCreateTime": 1579889421896,
    "lastProgramId": 2065063785,
    "lastProgramName": "霜雪千年（Cover:洛天依/乐正绫）",
    "name": "我向阳君很甜",
    "originalPrice": 0,
    "picId": 109951164271552620,
    "picUrl": "https://p2.music.126.net/5ye8098f7wkGCF8tPRthrw==/109951164271552628.jpg",
    "price": 0,
    "programCount": 23,
    "purchaseCount": 0,
    "radioFeeType": 0,
    "rcmdtext": "超甜女声向阳君",
    "subCount": 1470,
    "underShelf": false
     */
    private String category;
    private String categoryId;
    private String id;
    private String name;
    private String picUrl;
    private int price;
    private String programCount;
    private String rcmdtext;
    private String subCount;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProgramCount() {
        return programCount;
    }

    public void setProgramCount(String programCount) {
        this.programCount = programCount;
    }

    public String getRcmdtext() {
        return rcmdtext;
    }

    public void setRcmdtext(String rcmdtext) {
        this.rcmdtext = rcmdtext;
    }

    public String getSubCount() {
        return subCount;
    }

    public void setSubCount(String subCount) {
        this.subCount = subCount;
    }
}
