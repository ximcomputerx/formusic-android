package com.ximcomputerx.formusic.model;

import java.io.Serializable;

public class SearchHotInfo implements Serializable{
    private static final long serialVersionUID = 1L;

    /*
    "alg": "alg_statistics",
    "content": "一身正气荡人间 除暴安良我心愿",
    "iconType": 1,
    "iconUrl": "https://p1.music.126.net/2zQ0d1ThZCX5Jtkvks9aOQ==/109951163968000522.png",
    "score": 936206,
    "searchWord": "下山",
    "source": 0,
    "url": ""
    */

    private String searchWord;
    private String content;
    private String iconUrl;
    private String score;

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
