package com.ximcomputerx.formusic.model;

import java.io.Serializable;

public class MixInfo implements Serializable{
    private static final long serialVersionUID = 1L;

    /*
    "adType": 0,
    "alg": "alg_sq_featured",
    "anonimous": false,
    "cloudTrackCount": 0,
    "commentCount": 40,
    "commentThreadId": "A_PL_0_3174684129",
    "coverImgId": 109951164604586940,
    "coverImgId_str": "109951164604586939",
    "coverImgUrl": "http://p1.music.126.net/QBq3yVu3j8PIx84J53sKQg==/109951164604586939.jpg",
    "coverStatus": 3,
    "createTime": 1578049184504,
    "creator": {
        "accountStatus": 0,
        "authStatus": 1,
        "authority": 3,
        "avatarImgId": 1420569024374784,
        "avatarImgIdStr": "1420569024374784",
        "avatarUrl": "http://p1.music.126.net/QWMV-Ru_6149AKe0mCBXKg==/1420569024374784.jpg",
        "backgroundImgId": 2002210674180202,
        "backgroundImgIdStr": "2002210674180202",
        "backgroundUrl": "http://p1.music.126.net/pmHS4fcQtcNEGewNb5HRhg==/2002210674180202.jpg",
        "birthday": -2209017600000,
        "city": 110101,
        "defaultAvatar": false,
        "description": "网易云音乐官方账号",
        "detailDescription": "网易云音乐官方账号",
        "djStatus": 10,
        "followed": false,
        "gender": 1,
        "mutual": false,
        "nickname": "网易云音乐",
        "province": 110000,
        "signature": "网易云音乐是8亿人都在使用的音乐平台，致力于帮助音乐爱好者发现音乐惊喜，帮助音乐人实现梦想。
        "userId": 1,
        "userType": 2,
        "vipType": 11
    },
    "description": "哪有那么多过不去的坎和想不通的烦恼啊有时间焦虑还不如去吃顿好吃的如果不够，那就两顿吃完再去KTV吼上一嗓子比坐在家里冥思苦想有用得多哦~封面来自网络",
    "highQuality": false,
    "id": 3174684129,
    "name": "人生啊，没有吃顿饭唱个K不能解决的问题哦",
    "newImported": false,
    "ordered": true,
    "playCount": 297757,
    "privacy": 0,
    "shareCount": 2,
    "specialType": 0,
    "status": 0,
    "subscribedCount": 940,
    "subscribers": [
        {
            "accountStatus": 0,
            "authStatus": 0,
            "authority": 0,
            "avatarImgId": 109951164570404160,
            "avatarImgIdStr": "109951164570404162",
            "avatarImgId_str": "109951164570404162",
            "avatarUrl": "http://p1.music.126.net/gnbcp1XPMdb48B_tDpLOrA==/109951164570404162.jpg",
            "backgroundImgId": 109951164545239200,
            "backgroundImgIdStr": "109951164545239203",
            "backgroundUrl": "http://p1.music.126.net/VlY8T4rDsZETOL6P90qqJQ==/109951164545239203.jpg",
            "birthday": -2209017600000,
            "city": 310101,
            "defaultAvatar": false,
            "description": "",
            "detailDescription": "",
            "djStatus": 0,
            "followed": false,
            "gender": 2,
            "mutual": false,
            "nickname": "小恶魔MR呀",
            "province": 310000,
            "signature": "你是我的心心念念，一遍又一遍",
            "userId": 1619549533,
            "userType": 0,
            "vipType": 0
        }
    ],
    "tags": [
        "流行",
        "治愈",
        "放松"
    ],
    "totalDuration": 0,
    "trackCount": 30,
    "trackNumberUpdateTime": 1578049184637,
    "trackUpdateTime": 1578067338499,
    "updateTime": 1578141514063,
    "userId": 1
    */

    private String coverImgUrl;
    private String description;
    private String id;
    private String name;
    private String trackCount;

    public MixInfo() {
    }

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

    public String getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(String trackCount) {
        this.trackCount = trackCount;
    }
}
