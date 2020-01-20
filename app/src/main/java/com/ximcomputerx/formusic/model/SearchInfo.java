package com.ximcomputerx.formusic.model;

import java.io.Serializable;
import java.util.List;

public class SearchInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
    "album": {
        "artist": {
            "albumSize": 0,
            "alias": [],
            "id": 0,
            "img1v1": 0,
            "img1v1Url": "https://p2.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
            "name": "",
            "picId": 0
        },
        "copyrightId": 1416629,
        "id": 84645796,
        "mark": 0,
        "name": "羡慕",
        "picId": 109951164594343420,
        "publishTime": 1577894400000,
        "size": 1,
        "status": 1
    },
    "alias": [],
    "artists": [
        {
            "albumSize": 0,
            "alias": [],
            "id": 5771,
            "img1v1": 0,
            "img1v1Url": "https://p2.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
            "name": "许嵩",
            "picId": 0
        }
    ],
    "copyrightId": 1416629,
    "duration": 297642,
    "fee": 8,
    "ftype": 0,
    "id": 1413377028,
    "mark": 8192,
    "mvid": 10909661,
    "name": "羡慕",
    "rtype": 0,
    "status": 0
    */

    public class Album implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private String img1v1Url;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg1v1Url() {
            return img1v1Url;
        }

        public void setImg1v1Url(String img1v1Url) {
            this.img1v1Url = img1v1Url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public class Artists implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private String img1v1Url;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg1v1Url() {
            return img1v1Url;
        }

        public void setImg1v1Url(String img1v1Url) {
            this.img1v1Url = img1v1Url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    private String name;
    private String id;
    private String duration;
    private String score;
    private List<Artists> artists;
    private Album album;

    private String url;
    private String size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<Artists> getArtists() {
        return artists;
    }

    public void setArtists(List<Artists> artists) {
        this.artists = artists;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
