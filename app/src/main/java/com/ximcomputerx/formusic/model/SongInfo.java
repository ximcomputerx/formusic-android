package com.ximcomputerx.formusic.model;

import java.io.Serializable;
import java.util.List;

public class SongInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    {
        "al": {
            "id": 75019098,
            "name": "我曾",
            "pic": 109951164232796510,
            "picUrl": "http://p1.music.126.net/gNbAlXamNjhR2j3aOukNhg==/109951164232796511.jpg",
            "pic_str": "109951164232796511",
            "tns": []
        },
        "alia": [],
        "ar": [
            {
                "alias": [],
                "id": 12429072,
                "name": "隔壁老樊",
                "tns": []
            }
        ],
        "cd": "01",
        "cf": "",
        "copyright": 2,
        "cp": 0,
        "djId": 0,
        "dt": 285995,
        "fee": 8,
        "ftype": 0,
        "h": {
            "br": 320000,
            "fid": 0,
            "size": 11442721,
            "vd": 0
        },
        "id": 1336856777,
        "l": {
            "br": 128000,
            "fid": 0,
            "size": 4577115,
            "vd": 0
        },
        "m": {
            "br": 192000,
            "fid": 0,
            "size": 6865650,
            "vd": 0
        },
        "mark": 32768,
        "mst": 9,
        "mv": 10858662,
        "name": "我曾",
        "no": 1,
        "pop": 1882869,
        "pst": 0,
        "publishTime": 1549123200007,
        "rtUrls": [],
        "rtype": 0,
        "s_id": 0,
        "st": 0,
        "t": 0,
        "v": 27
     }
    */

    public class Al implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private String picUrl;

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

    public class Ar implements Serializable {
        private static final long serialVersionUID = 1L;
        private String id;
        private String name;

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
    }

    private String id;
    private String name;
    private Al al;
    private List<Ar> ar;
    private String url;
    private String size;
    private int fee;

    public SongInfo(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public SongInfo() {
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

    public Al getAl() {
        return al;
    }

    public void setAl(Al al) {
        this.al = al;
    }

    public List<Ar> getAr() {
        return ar;
    }

    public void setAr(List<Ar> ar) {
        this.ar = ar;
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

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}
