package com.ximcomputerx.formusic.model;

import com.ximcomputerx.formusic.config.Constant;

import java.io.Serializable;

public class RankInfo implements Serializable {
    /*
    "ToplistType": "S",
    "adType": 0,
    "anonimous": false,
    "backgroundCoverId": 0,
    "cloudTrackCount": 0,
    "commentThreadId": "A_PL_0_19723756",
    "coverImgId": 18696095720518496,
    "coverImgId_str": "18696095720518497",
    "coverImgUrl": "http://p1.music.126.net/DrRIg6CrgDfVLEph9SNh7w==/18696095720518497.jpg",
    "createTime": 1404115136883,
    "description": "云音乐中每天热度上升最快的100首单曲，每日更新。",
    "highQuality": false,
    "id": 19723756,
    "name": "云音乐飙升榜",
    "newImported": false,
    "opRecommend": false,
    "ordered": true,
    "playCount": 2718203904,
    "privacy": 0,
    "specialType": 10,
    "status": 0,
    "subscribedCount": 2653326,
    "subscribers": [],
    "tags": [],
    "titleImage": 0,
    "totalDuration": 0,
    "trackCount": 100,
    "trackNumberUpdateTime": 1578096801969,
    "trackUpdateTime": 1578129206039,
    "updateFrequency": "每天更新",
    "updateTime": 1578096803408,
    "userId": 1
    */

    private String coverImgUrl;
    private String description;
    private String id;
    private String name;
    private String updateFrequency;

    private int type = Constant.RANK_TYPE_other;

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getUpdateFrequency() {
        return updateFrequency;
    }

    public void setUpdateFrequency(String updateFrequency) {
        this.updateFrequency = updateFrequency;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
