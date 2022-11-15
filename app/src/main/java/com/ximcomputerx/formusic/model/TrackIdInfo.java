package com.ximcomputerx.formusic.model;

import java.io.Serializable;

public class TrackIdInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    {
                "id": 1409382131,
                "v": 14,
                "t": 0,
                "at": 1594971357982,
                "alg": null,
                "uid": 30728956,
                "rcmdReason": "",
                "sc": null,
                "f": null,
                "lr": 0
            }
    */

    private String id;
    private String v;
    private String t;
    private String at;
    private String alg;
    private String uid;
    private String rcmdReason;
    private String sc;
    private String f;
    private String lr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRcmdReason() {
        return rcmdReason;
    }

    public void setRcmdReason(String rcmdReason) {
        this.rcmdReason = rcmdReason;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getLr() {
        return lr;
    }

    public void setLr(String lr) {
        this.lr = lr;
    }
}
