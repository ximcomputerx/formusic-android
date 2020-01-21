package com.ximcomputerx.formusic.model;

import java.io.Serializable;

/**
 * Created by Neo on 15/7/14.
 */
public class ApkDownloadInfo implements Serializable {

    /**
     * 版本号
     */
    private static final long serialVersionUID = 574845850345231235L;

    /**
     * 远程文件名称
     */
    private String remoteFile;

    /**
     * 本地文件名称
     */
    private String localFile;

    /**
     * 历史版本文件名称
     */
    private String oldLocalFile;

    /**
     * 临时文件名称
     */
    private String tempLocalFile;

    public String getRemoteFile() {
        return remoteFile;
    }

    public void setRemoteFile(String remoteFile) {
        this.remoteFile = remoteFile;
    }

    public String getLocalFile() {
        return localFile;
    }

    public void setLocalFile(String localFile) {
        this.localFile = localFile;
    }

    public String getOldLocalFile() {
        return oldLocalFile;
    }

    public void setOldLocalFile(String oldLocalFile) {
        this.oldLocalFile = oldLocalFile;
    }

    public String getTempLocalFile() {
        return tempLocalFile;
    }

    public void setTempLocalFile(String tempLocalFile) {
        this.tempLocalFile = tempLocalFile;
    }

}
